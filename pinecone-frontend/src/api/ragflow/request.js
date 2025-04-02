import { ElMessage } from "element-plus";

const baseURL = import.meta.env.VITE_RAGFLOW_BASE_API;

async function request(config) {
  const { url, method = "GET", data, responseType } = config;

  const headers = {
    Authorization: `Bearer ${import.meta.env.VITE_RAGFLOW_API_KEY}`,
    "Content-Type": "application/json",
  };

  if (data?.stream) {
    headers["Accept"] = "text/event-stream";
  }

  try {
    const response = await fetch(`${baseURL}${url}`, {
      method,
      headers,
      body: data ? JSON.stringify(data) : undefined,
    });

    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message || "请求失败");
    }

    if (data?.stream) {
      return response;
    }

    const result = await response.json();
    if (result.code === 0) {
      return result;
    }

    throw new Error(result.message || "请求失败");
  } catch (error) {
    console.error("请求错误:", error);
    ElMessage.error(error.message || "请求失败");
    throw error;
  }
}

export default request;
