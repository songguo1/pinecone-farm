import request from "@/api/ragflow/request";

// 处理流式响应的辅助函数
export function handleStreamResponse(response, onData, onError, onComplete) {
  if (!response.body) {
    onError && onError(new Error("无效的响应数据"));
    return;
  }

  const reader = response.body.getReader();
  const decoder = new TextDecoder();
  let buffer = "";

  function processText(text) {
    buffer += text;
    const lines = buffer.split("\n");
    buffer = lines.pop();

    for (const line of lines) {
      if (line.trim() === "") continue;

      try {
        const cleanedLine = line.startsWith("data:")
          ? line.slice(5).trim()
          : line.trim();
        if (!cleanedLine) continue;

        const data = JSON.parse(cleanedLine);

        if (data.code !== 0) {
          onError && onError(new Error(data.message));
          return;
        }

        if (data.data === true) {
          onComplete && onComplete();
          return;
        }

        if (data.data && data.data.answer) {
          onData && onData(data.data.answer);
        }
      } catch (e) {
        console.warn("JSON解析失败，原始数据:", line);
      }
    }
  }

  async function read() {
    try {
      while (true) {
        const { done, value } = await reader.read();
        if (done) {
          if (buffer) {
            processText(buffer);
          }
          onComplete && onComplete();
          return;
        }
        processText(decoder.decode(value, { stream: true }));
      }
    } catch (error) {
      console.error("读取流数据失败:", error);
      onError && onError(error);
    }
  }

  read();
}

// 创建chat assistant会话
export function createChatSession(data) {
  const chatId = data.chat_id || import.meta.env.VITE_CHAT_ID;
  delete data.chat_id; // 从请求数据中移除chat_id，避免发送到服务器

  return request({
    url: `/api/v1/chats/${chatId}/sessions`,
    method: "post",
    data: data,
  });
}

// 获取chat assistant列表
export function converseWithChat(data) {
  const chatId = data.chat_id || import.meta.env.VITE_CHAT_ID;
  delete data.chat_id; // 从请求数据中移除chat_id，避免发送到服务器

  return request({
    url: `/api/v1/chats/${chatId}/completions`,
    method: "post",
    data: data,
  });
}
