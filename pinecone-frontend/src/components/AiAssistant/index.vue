<template>
  <div v-if="dialogVisible" class="ai-assistant-modal">
    <div class="modal-mask" @click="handleClose"></div>
    <div class="modal-wrapper">
      <div class="modal-container">
        <div class="modal-header">
          <span class="modal-title">AI 助手</span>
          <div class="knowledge-base-selector">
            <el-radio-group
              v-model="currentKnowledgeBase"
              @change="handleKnowledgeBaseChange"
            >
              <el-radio-button label="base">基地信息</el-radio-button>
              <el-radio-button label="plant">种植规范</el-radio-button>
              <el-radio-button label="disease">病虫害</el-radio-button>
              <el-radio-button label="equipment">设备操作</el-radio-button>
            </el-radio-group>
          </div>
          <el-icon class="close-icon" @click="handleClose"><Close /></el-icon>
        </div>
        <div class="chat-container">
          <div v-if="!sessionId" class="initializing-message">
            正在初始化聊天...
          </div>

          <template v-else>
            <div class="chat-messages" ref="messageContainer">
              <div
                v-for="(message, index) in messages"
                :key="index"
                :class="['message', message.role]"
              >
                <div class="message-content">
                  <div class="avatar">
                    <img
                      :src="message.role === 'user' ? userAvatar : aiAvatar"
                    />
                  </div>
                  <div class="text">
                    <template v-if="message.role === 'assistant'">
                      <div v-if="message.think" class="think-box">
                        <div class="think-header">
                          <el-icon><Sunny /></el-icon>
                          思考过程
                        </div>
                        <div
                          class="think-content"
                          :class="{ thinking: message.isThinking }"
                          v-html="renderMarkdown(message.think)"
                        ></div>
                      </div>
                      <div
                        class="answer-content"
                        :class="{ typing: !message.isThinking && loading }"
                        v-html="renderMarkdown(message.answer)"
                      ></div>
                    </template>
                    <template v-else>
                      <div v-html="renderMarkdown(message.content)"></div>
                    </template>
                  </div>
                </div>
                <div class="time">{{ message.time }}</div>
              </div>
              <div v-if="currentResponse" class="message assistant">
                <div v-html="renderMarkdown(currentResponse)"></div>
              </div>
            </div>

            <div class="input-area">
              <el-input
                v-model="inputMessage"
                :disabled="loading"
                type="textarea"
                :rows="3"
                placeholder="请输入您的问题..."
                @keyup.enter="sendMessage"
              >
                <template #append>
                  <el-button
                    :loading="loading"
                    type="primary"
                    @click="sendMessage"
                  >
                    发送
                  </el-button>
                </template>
              </el-input>
            </div>
          </template>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from "vue";
import { ElMessage } from "element-plus";
import {
  createChatSession,
  converseWithChat,
  handleStreamResponse,
} from "@/api/ragflow/chat";
import userAvatar from "@/assets/screen/user-avatar.png";
import aiAvatar from "@/assets/screen/ai-avatar.png";
import { Sunny, Close } from "@element-plus/icons-vue";
import MarkdownIt from "markdown-it";

const props = defineProps({
  modelValue: Boolean,
});

const emit = defineEmits(["update:modelValue"]);

const dialogVisible = ref(props.modelValue);
const inputMessage = ref("");
const loading = ref(false);
const messages = ref([]);
const currentResponse = ref("");
const assistantId = ref(null);
const sessionId = ref(null);
const messageContainer = ref(null);

// 添加知识库相关的状态
const currentKnowledgeBase = ref("base");
const knowledgeBaseMap = {
  base: import.meta.env.VITE_BASE_CHAT_ID,
  plant: import.meta.env.VITE_PLANT_CHAT_ID,
  disease: import.meta.env.VITE_DISEASE_CHAT_ID,
  equipment: import.meta.env.VITE_EQUIPMENT_CHAT_ID,
};

const knowledgeBaseLabels = {
  base: "基地信息",
  plant: "种植规范",
  disease: "病虫害",
  equipment: "设备操作",
};

// 初始化 markdown-it
const md = new MarkdownIt({
  html: true,
  breaks: true,
  linkify: true,
  typographer: true,
});

// 添加 markdown 渲染函数
const renderMarkdown = (content) => {
  if (!content) return "";
  try {
    return md.render(content);
  } catch (error) {
    console.error("Markdown rendering error:", error);
    return content;
  }
};

// 初始化助手和会话
const initChat = async () => {
  try {
    loading.value = true;
    console.log("开始初始化聊天...");
    const sessionResponse = await createChatSession({
      name: "智慧农业",
      chat_id: knowledgeBaseMap[currentKnowledgeBase.value], // 传递当前选中的知识库ID
    });
    console.log("会话创建响应:", sessionResponse);

    if (!sessionResponse.data?.id) {
      throw new Error("创建会话失败: " + JSON.stringify(sessionResponse));
    }

    sessionId.value = sessionResponse.data.id;
    console.log("聊天会话创建成功:", sessionId.value);

    // 添加欢迎消息
    messages.value = [
      {
        role: "assistant",
        answer: `您好！我是智慧农业AI助手，很高兴为您服务，你可以向我提问关于${
          knowledgeBaseLabels[currentKnowledgeBase.value]
        }的问题。`,
        think: "",
        isThinking: false,
      },
    ];
  } catch (error) {
    console.error("初始化聊天失败:", error);
    ElMessage.error(error.message || "初始化聊天失败，请重试");
    throw error;
  } finally {
    loading.value = false;
  }
};

watch(
  () => props.modelValue,
  async (val) => {
    dialogVisible.value = val;
    if (val && !sessionId.value) {
      await initChat();
    }
  }
);

watch(dialogVisible, (val) => {
  emit("update:modelValue", val);
});

const processAIResponse = (content) => {
  // 检查是否包含完整的think标签
  const thinkMatch = content.match(/<think>(.*?)<\/think>\s*(.*)/s);
  if (thinkMatch) {
    return {
      isThinking: false,
      think: thinkMatch[1].trim(),
      answer: thinkMatch[2].trim(),
    };
  }
  // 检查是否正在处理think标签
  const partialThinkMatch = content.match(/<think>(.*)/s);
  if (partialThinkMatch) {
    return {
      isThinking: true,
      think: partialThinkMatch[1].trim(),
      answer: "",
    };
  }
  // 检查是否是think标签的后续内容
  if (content.includes("</think>")) {
    const parts = content.split("</think>");
    return {
      isThinking: false,
      think: parts[0].trim(),
      answer: parts[1].trim(),
    };
  }
  // 普通回答内容
  return {
    isThinking: false,
    think: null,
    answer: content,
  };
};

// 处理知识库切换
const handleKnowledgeBaseChange = async (value) => {
  // 清空当前会话
  sessionId.value = null;
  messages.value = [];

  // 重新初始化聊天
  await initChat();
};

const sendMessage = async () => {
  if (!inputMessage.value.trim() || loading.value) return;

  if (!sessionId.value) {
    await initChat();
    if (!sessionId.value) {
      ElMessage.error("无法创建聊天会话，请重试");
      return;
    }
  }

  const userMessage = {
    role: "user",
    content: inputMessage.value.trim(),
    time: new Date().toLocaleTimeString(),
  };

  messages.value.push(userMessage);
  loading.value = true;

  try {
    const response = await converseWithChat({
      question: inputMessage.value.trim(),
      stream: true,
      session_id: sessionId.value,
      chat_id: knowledgeBaseMap[currentKnowledgeBase.value], // 传递当前选中的知识库ID
    });

    // 添加 AI 的初始消息
    const aiMessage = {
      role: "assistant",
      think: "",
      answer: "",
      isThinking: false,
      time: new Date().toLocaleTimeString(),
    };
    messages.value.push(aiMessage);

    let accumulatedThink = "";
    let accumulatedAnswer = "";
    let isInThinkingMode = false;

    handleStreamResponse(
      response,
      (content) => {
        const lastMessage = messages.value[messages.value.length - 1];
        const processed = processAIResponse(content);

        if (processed.think !== null) {
          // 更新思考内容
          if (processed.isThinking) {
            isInThinkingMode = true;
            accumulatedThink = processed.think;
          } else {
            accumulatedThink = processed.think;
            accumulatedAnswer = processed.answer;
            isInThinkingMode = false;
          }
        } else if (isInThinkingMode) {
          // 继续累积思考内容
          accumulatedThink += content;
        } else {
          // 累积回答内容
          accumulatedAnswer += content;
        }

        // 更新消息
        lastMessage.think = accumulatedThink;
        lastMessage.answer = accumulatedAnswer;
        lastMessage.isThinking = isInThinkingMode;

        scrollToBottom();
      },
      (error) => {
        console.error("Stream error:", error);
        ElMessage.error(error.message || "接收消息失败");
        loading.value = false;
      },
      () => {
        loading.value = false;
        scrollToBottom();
      }
    );
  } catch (error) {
    console.error("发送消息失败:", error);
    ElMessage.error(error.message || "发送消息失败");
    loading.value = false;
  }

  inputMessage.value = "";
};

// 滚动到底部
const scrollToBottom = () => {
  const container = messageContainer.value;
  if (container) {
    container.scrollTop = container.scrollHeight;
  }
};

const handleClose = () => {
  dialogVisible.value = false;
  emit("update:modelValue", false);
};

// 组件挂载时初始化聊天（可选）
onMounted(() => {
  if (dialogVisible.value) {
    initChat();
  }
});
</script>

<style scoped>
.ai-assistant-modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 2000;
  display: flex;
  justify-content: center;
  align-items: center;
  animation: fadeIn 0.3s ease-out;
}

.modal-mask {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.7);
  backdrop-filter: blur(3px);
}

.modal-wrapper {
  position: relative;
  width: 90%;
  height: 90%;
  max-width: 1200px;
  min-height: 600px;
  z-index: 1;
}

.modal-container {
  width: 100%;
  height: 100%;
  background: rgba(6, 50, 102, 0.95);
  border: 1px solid #7cfffd;
  box-shadow: 0 0 20px rgba(124, 255, 253, 0.3);
  border-radius: 8px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  backdrop-filter: blur(10px);
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 20px;
  border-bottom: 1px solid rgba(124, 255, 253, 0.3);
  background: rgba(6, 50, 102, 0.8);
}

.modal-title {
  color: #7cfffd;
  font-size: 18px;
  font-weight: bold;
  letter-spacing: 2px;
  text-shadow: 0 0 10px rgba(124, 255, 253, 0.5);
}

.close-icon {
  color: #7cfffd;
  cursor: pointer;
  font-size: 20px;
  transition: all 0.3s ease;
}

.close-icon:hover {
  color: #fff;
  transform: rotate(90deg);
}

.chat-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.initializing-message {
  text-align: center;
  padding: 20px;
  color: #7cfffd;
  font-size: 16px;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
  background: rgba(6, 50, 102, 0.3);
  scroll-behavior: smooth;
}

.message {
  margin-bottom: 12px;
  animation: slideIn 0.3s ease-out;
}

@keyframes slideIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.message-content {
  display: flex;
  align-items: flex-start;
  gap: 12px;
}

.avatar img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  border: 2px solid #7cfffd;
  box-shadow: 0 0 10px rgba(124, 255, 253, 0.3);
}

.text {
  background: rgba(124, 255, 253, 0.1);
  padding: 10px 12px;
  border-radius: 8px;
  color: #fff;
  max-width: 80%;
  width: fit-content;
  box-shadow: 0 2px 8px rgba(124, 255, 253, 0.1);
  transition: all 0.3s ease;
}

.text:hover {
  box-shadow: 0 4px 12px rgba(124, 255, 253, 0.2);
}

.time {
  font-size: 12px;
  color: #7cfffd;
  margin-top: 2px;
  margin-left: 52px;
  opacity: 0.8;
}

.message.user {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
}

.message.user .message-content {
  flex-direction: row-reverse;
}

.message.user .text {
  background: rgba(124, 255, 253, 0.2);
  max-width: 90%;
}

.message.user .time {
  margin-right: 52px;
  margin-left: 0;
}

.input-area {
  padding: 20px;
  background: rgba(6, 50, 102, 0.3);
  border-top: 1px solid rgba(124, 255, 253, 0.3);
}

.input-area .el-button {
  margin-top: 10px;
  width: 100px;
  float: right;
  background: rgba(124, 255, 253, 0.2);
  border-color: #7cfffd;
  color: #7cfffd;
  transition: all 0.3s ease;
}

.input-area .el-button:hover {
  background: rgba(124, 255, 253, 0.3);
  transform: translateY(-2px);
}

:deep(.el-textarea__inner) {
  background: rgba(6, 50, 102, 0.5);
  border: 1px solid rgba(124, 255, 253, 0.3);
  color: #fff;
  transition: all 0.3s ease;
}

:deep(.el-textarea__inner:focus) {
  border-color: #7cfffd;
  box-shadow: 0 0 10px rgba(124, 255, 253, 0.3);
}

/* 添加禁用状态下的样式 */
:deep(.el-textarea__inner:disabled) {
  background: rgba(6, 50, 102, 0.5);
  border: 1px solid rgba(124, 255, 253, 0.2);
  color: rgba(255, 255, 255, 0.6);
  cursor: not-allowed;
}

.think-box {
  margin-bottom: 8px;
  background: rgba(255, 223, 186, 0.1);
  border-radius: 8px;
  padding: 8px 10px;
  border-left: 3px solid #ffd700;
  box-shadow: 0 2px 4px rgba(255, 215, 0, 0.1);
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0% {
    box-shadow: 0 2px 4px rgba(255, 215, 0, 0.1);
  }
  50% {
    box-shadow: 0 2px 8px rgba(255, 215, 0, 0.2);
  }
  100% {
    box-shadow: 0 2px 4px rgba(255, 215, 0, 0.1);
  }
}

.think-header {
  color: #ffd700;
  font-size: 14px;
  font-weight: bold;
  margin-bottom: 4px;
  display: flex;
  align-items: center;
  gap: 8px;
}

.think-header :deep(.el-icon) {
  font-size: 16px;
}

.think-content {
  color: #ffd700;
  font-size: 13px;
  line-height: 1.4;
  font-family: "Courier New", monospace;
}

.answer-content {
  color: #fff;
  line-height: 1.4;
  margin-top: 6px;
}

/* 移除打字效果相关样式 */
.answer-content.typing {
  border-right: none;
  animation: none;
}

.think-content.thinking {
  border-right: none;
  animation: none;
}

@keyframes typing {
  /* 移除打字动画 */
}

.message.assistant .text {
  background: rgba(6, 50, 102, 0.5);
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

/* 添加 markdown 内容的样式控制 */
:deep(.text) {
  p {
    margin: 0;
    padding: 0;
  }

  /* 控制换行和空白符的显示 */
  p,
  li,
  pre,
  blockquote {
    white-space: normal;
  }

  /* 只在代码块中保留空白符 */
  pre code {
    white-space: pre;
  }
}

.knowledge-base-selector {
  flex: 1;
  text-align: center;
  margin: 0 20px;
}

:deep(.el-radio-button__inner) {
  background: rgba(124, 255, 253, 0.1);
  border-color: #7cfffd;
  color: #7cfffd;
  transition: all 0.3s ease;
}

:deep(.el-radio-button__original-radio:checked + .el-radio-button__inner) {
  background: rgba(124, 255, 253, 0.3);
  border-color: #7cfffd;
  color: #fff;
  box-shadow: -1px 0 0 0 #7cfffd;
}

:deep(.el-radio-button:first-child .el-radio-button__inner) {
  border-left-color: #7cfffd;
}

:deep(.el-radio-button__inner:hover) {
  color: #fff;
  background: rgba(124, 255, 253, 0.2);
}
</style>
