<template>
  <div class="login">
    <el-form
      ref="loginRef"
      :model="loginForm"
      :rules="loginRules"
      class="login-form"
    >
      <h3 class="title">松果智慧农业系统</h3>
      <el-form-item prop="username">
        <el-input
          v-model="loginForm.username"
          type="text"
          size="large"
          auto-complete="off"
          placeholder="账号"
        >
          <template #prefix
            ><svg-icon
              icon-class="farmer"
              style="width: 25px; height: 25px; color: #e8c97b"
              class="el-input__icon input-icon"
          /></template>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          v-model="loginForm.password"
          type="password"
          size="large"
          auto-complete="off"
          placeholder="密码"
          @keyup.enter="handleLogin"
        >
          <template #prefix
            ><svg-icon
              icon-class="wheat"
              style="width: 25px; height: 25px; color: #e8c97b"
              class="el-input__icon input-icon"
          /></template>
        </el-input>
      </el-form-item>
      <el-form-item prop="code" v-if="captchaEnabled">
        <el-input
          v-model="loginForm.code"
          size="large"
          auto-complete="off"
          placeholder="验证码"
          style="width: 63%"
          @keyup.enter="handleLogin"
        >
          <template #prefix
            ><svg-icon
              icon-class="wheat"
              style="width: 25px; height: 25px; color: #e8c97b"
              class="el-input__icon input-icon"
          /></template>
        </el-input>
        <div class="login-code">
          <img :src="codeUrl" @click="getCode" class="login-code-img" />
        </div>
      </el-form-item>
      <el-checkbox
        v-model="loginForm.rememberMe"
        style="margin: 0px 0px 25px 0px"
        class="remember-checkbox"
        >记住密码</el-checkbox
      >
      <el-form-item style="width: 100%">
        <el-button
          :loading="loading"
          size="large"
          type="primary"
          class="login-button"
          @click.prevent="handleLogin"
        >
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
        <div style="float: right" v-if="register">
          <router-link class="link-type" :to="'/register'"
            >立即注册</router-link
          >
        </div>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <!-- <div class="el-login-footer">
      <span>Copyright © 2018-2023 ruoyi.vip All Rights Reserved.</span>
    </div> -->
  </div>
</template>

<script setup>
import { getCodeImg } from "@/api/login";
import Cookies from "js-cookie";
import { encrypt, decrypt } from "@/utils/jsencrypt";
import useUserStore from "@/store/modules/user";

const userStore = useUserStore();
const route = useRoute();
const router = useRouter();
const { proxy } = getCurrentInstance();

const loginForm = ref({
  username: "admin",
  password: "admin123",
  rememberMe: false,
  code: "",
  uuid: "",
});

const loginRules = {
  username: [{ required: true, trigger: "blur", message: "请输入您的账号" }],
  password: [{ required: true, trigger: "blur", message: "请输入您的密码" }],
  code: [{ required: true, trigger: "change", message: "请输入验证码" }],
};

const codeUrl = ref("");
const loading = ref(false);
// 验证码开关
const captchaEnabled = ref(true);
// 注册开关
const register = ref(false);
const redirect = ref(undefined);

watch(
  route,
  (newRoute) => {
    redirect.value = newRoute.query && newRoute.query.redirect;
  },
  { immediate: true }
);

function handleLogin() {
  proxy.$refs.loginRef.validate((valid) => {
    if (valid) {
      loading.value = true;
      // 勾选了需要记住密码设置在 cookie 中设置记住用户名和密码
      if (loginForm.value.rememberMe) {
        Cookies.set("username", loginForm.value.username, { expires: 30 });
        Cookies.set("password", encrypt(loginForm.value.password), {
          expires: 30,
        });
        Cookies.set("rememberMe", loginForm.value.rememberMe, { expires: 30 });
      } else {
        // 否则移除
        Cookies.remove("username");
        Cookies.remove("password");
        Cookies.remove("rememberMe");
      }
      // 调用action的登录方法
      userStore
        .login(loginForm.value)
        .then(() => {
          const query = route.query;
          const otherQueryParams = Object.keys(query).reduce((acc, cur) => {
            if (cur !== "redirect") {
              acc[cur] = query[cur];
            }
            return acc;
          }, {});
          router.push({ path: redirect.value || "/", query: otherQueryParams });
        })
        .catch(() => {
          loading.value = false;
          // 重新获取验证码
          if (captchaEnabled.value) {
            getCode();
          }
        });
    }
  });
}

function getCode() {
  getCodeImg().then((res) => {
    captchaEnabled.value =
      res.captchaEnabled === undefined ? true : res.captchaEnabled;
    if (captchaEnabled.value) {
      codeUrl.value = "data:image/gif;base64," + res.img;
      loginForm.value.uuid = res.uuid;
    }
  });
}

function getCookie() {
  const username = Cookies.get("username");
  const password = Cookies.get("password");
  const rememberMe = Cookies.get("rememberMe");
  loginForm.value = {
    username: username === undefined ? loginForm.value.username : username,
    password:
      password === undefined ? loginForm.value.password : decrypt(password),
    rememberMe: rememberMe === undefined ? false : Boolean(rememberMe),
  };
}

getCode();
getCookie();
</script>

<style lang="scss" scoped>
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-color: #3a8b50;
  background-image: url("../assets/images/loginBg.svg");
  background-size: 85%;
  background-position: center;
  background-repeat: no-repeat;
}
.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #3a8b50;
  font-size: 24px;
  font-weight: bold;
}

.login-form {
  border-radius: 12px;
  background: #ffffff;
  width: 420px;
  padding: 35px 30px 20px 30px;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);

  .el-form-item {
    margin-bottom: 25px; /* 增加字段间距为1.5倍行距 */
  }

  .el-input {
    height: 46px;

    input {
      height: 46px;
      font-size: 16px;
    }
  }

  .input-icon {
    height: 42px;
    width: 18px;
    margin-left: 5px;
    color: #3a8b50;
  }

  .remember-checkbox {
    color: #666;
    font-size: 14px;

    .el-checkbox__input.is-checked .el-checkbox__inner {
      background-color: #3a8b50;
      border-color: #3a8b50;
    }

    .el-checkbox__input.is-checked + .el-checkbox__label {
      color: #3a8b50;
    }
  }

  .login-button {
    width: 100%;
    height: 50px;
    border-radius: 8px;
    background-color: #3a8b50;
    border-color: #3a8b50;
    font-size: 16px;
    font-weight: bold;

    &:hover,
    &:focus {
      background-color: #2d6e3e;
      border-color: #2d6e3e;
    }
  }

  .link-type {
    color: #3a8b50;
    text-decoration: none;

    &:hover {
      color: #2d6e3e;
      text-decoration: underline;
    }
  }
}

.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}

.login-code {
  width: 33%;
  height: 46px;
  float: right;

  img {
    cursor: pointer;
    vertical-align: middle;
    border-radius: 4px;
    height: 46px;
    padding-left: 12px;
  }
}

.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}

:deep(.el-button--primary) {
  --el-button-bg-color: #3a8b50;
  --el-button-border-color: #3a8b50;
  --el-button-hover-bg-color: #2d6e3e;
  --el-button-hover-border-color: #2d6e3e;
  --el-button-active-bg-color: #2d6e3e;
  --el-button-active-border-color: #2d6e3e;
}

:deep(.el-checkbox__input.is-checked .el-checkbox__inner) {
  background-color: #3a8b50;
  border-color: #3a8b50;
}

:deep(.el-checkbox__input.is-checked + .el-checkbox__label) {
  color: #3a8b50;
}
</style>
