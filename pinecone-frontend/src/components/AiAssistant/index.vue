<template>
  <el-dialog
    v-model="dialogVisible"
    title="AI 助手"
    width="50%"
    :close-on-click-modal="false"
    :before-close="handleClose"
    class="ai-dialog"
    destroy-on-close
  >
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
                <img :src="message.role === 'user' ? userAvatar : aiAvatar" />
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
                    >
                      {{ message.think }}
                    </div>
                  </div>
                  <div
                    class="answer-content"
                    :class="{ typing: !message.isThinking && loading }"
                  >
                    {{ message.answer }}
                  </div>
                </template>
                <template v-else>
                  {{ message.content }}
                </template>
              </div>
            </div>
            <div class="time">{{ message.time }}</div>
          </div>
          <div v-if="currentResponse" class="message assistant">
            {{ currentResponse }}
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
              <el-button :loading="loading" type="primary" @click="sendMessage">
                发送
              </el-button>
            </template>
          </el-input>
        </div>
      </template>
    </div>
  </el-dialog>
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
import { Sunny } from "@element-plus/icons-vue";

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

// 初始化助手和会话
const initChat = async () => {
  try {
    loading.value = true;
    console.log("开始初始化聊天...");
    const sessionResponse = await createChatSession({
      name: "智慧农业",
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
        answer:
          "您好！我是智慧农业AI助手，很高兴为您服务。请问有什么可以帮您？",
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
    });

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

// 组件卸载时清理
const handleClose = async (done) => {
  // 可以在这里添加清理会话的逻辑
  done();
};

// 组件挂载时初始化聊天（可选）
onMounted(() => {
  if (dialogVisible.value) {
    initChat();
  }
});
</script>

<style scoped>
.chat-container {
  height: 60vh;
  display: flex;
  flex-direction: column;
}

.initializing-message {
  text-align: center;
  padding: 20px;
  color: #909399;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
  background: rgba(6, 50, 102, 0.3);
  scroll-behavior: smooth;
}

.message {
  margin-bottom: 20px;
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
}

.text {
  background: rgba(124, 255, 253, 0.1);
  padding: 16px;
  border-radius: 8px;
  color: #fff;
  max-width: 80%;
  width: fit-content;
  box-shadow: 0 2px 8px rgba(124, 255, 253, 0.1);
}

.time {
  font-size: 12px;
  color: #7cfffd;
  margin-top: 4px;
  margin-left: 52px;
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
}

:deep(.el-textarea__inner) {
  background: rgba(6, 50, 102, 0.5);
  border: 1px solid rgba(124, 255, 253, 0.3);
  color: #fff;
}

:deep(.el-textarea__inner:focus) {
  border-color: #7cfffd;
}

:deep(.ai-dialog .el-dialog) {
  background: rgba(6, 50, 102, 0.9);
  border: 1px solid #7cfffd;
  box-shadow: 0 0 20px rgba(124, 255, 253, 0.3);
}

:deep(.ai-dialog .el-dialog__header) {
  border-bottom: 1px solid rgba(124, 255, 253, 0.3);
}

:deep(.ai-dialog .el-dialog__title) {
  color: #7cfffd;
}

:deep(.ai-dialog .el-dialog__headerbtn .el-dialog__close) {
  color: #7cfffd;
}

:deep(.ai-dialog .el-button) {
  background: transparent;
  border-color: #7cfffd;
  color: #7cfffd;
}

:deep(.ai-dialog .el-button--primary) {
  background: rgba(124, 255, 253, 0.2);
}

:deep(.ai-dialog .el-button:hover) {
  background: rgba(124, 255, 253, 0.3);
  border-color: #7cfffd;
  color: #fff;
}

.think-box {
  margin-bottom: 12px;
  background: rgba(255, 223, 186, 0.1);
  border-radius: 8px;
  padding: 12px;
  border-left: 3px solid #ffd700;
  box-shadow: 0 2px 4px rgba(255, 215, 0, 0.1);
}

.think-header {
  color: #ffd700;
  font-size: 14px;
  font-weight: bold;
  margin-bottom: 8px;
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
  line-height: 1.5;
  white-space: pre-wrap;
  font-family: "Courier New", monospace;
}

.answer-content {
  color: #fff;
  line-height: 1.6;
  margin-top: 10px;
  white-space: pre-wrap;
}

.answer-content.typing {
  border-right: 2px solid #7cfffd;
  animation: typing 0.5s steps(1) infinite;
}

@keyframes typing {
  0% {
    border-color: transparent;
  }
  50% {
    border-color: #7cfffd;
  }
  100% {
    border-color: transparent;
  }
}

.message.assistant .text {
  background: rgba(6, 50, 102, 0.5);
}

.think-content.thinking {
  border-right: 2px solid #ffd700;
  animation: typing 0.5s steps(1) infinite;
}

.answer-content.typing {
  border-right: 2px solid #7cfffd;
  animation: typing 0.5s steps(1) infinite;
}

@keyframes typing {
  0% {
    border-color: transparent;
  }
  50% {
    border-color: currentColor;
  }
  100% {
    border-color: transparent;
  }
}

.think-content,
.answer-content {
  transition: all 0.1s ease-out;
}
</style>
