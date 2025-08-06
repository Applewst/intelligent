<script setup>
import { reactive, ref } from "vue";
import { ElMessage } from "element-plus";
import { useCounterStore } from "../stores/counter";
const store = useCounterStore();
import { useRouter } from 'vue-router';
const router = useRouter();

  
    // 当前表单状态，初始为登录表单
    const currentForm = ref("login");
    // 记住密码状态，初始为不记住密码
    const rememberMe = ref(false);

    // 登录表单的数据模型
    const loginForm = reactive({
      username: "",
      password: "",
    });


    // 注册表单的数据模型
    const registerForm = reactive({
      username: "",
      password: "",
      confirmPassword: "",
    });

    // 登录表单的校验规则
    const loginRules = {
      username: [
        { required: true, message: "请输入用户名", trigger: "blur" },
        {
          pattern:/^[\u4e00-\u9fa5a-zA-Z0-9_]{2,10}$/,
          message: "用户名格式不正确！仅允许汉字，字母、数字和下划线，长度为 2-10",
          trigger: "blur",
        },
      ],
      password: [
        { required: true, message: "请输入密码", trigger: "blur" },
        {
          pattern: /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{6,20}$/,
          message: "密码格式不正确！至少包含一个字母、一个数字，长度为 6-20",
          trigger: "blur",
        },
      ],
    };

    // 注册表单的校验规则
    const registerRules = {
      username: [
        { required: true, message: "请输入用户名", trigger: "blur" },
        {
          pattern: /^[\u4e00-\u9fa5a-zA-Z0-9_]{2,10}$/,
          message: "用户名格式不正确！仅允许汉字，字母、数字和下划线，长度为 2-10",
          trigger: "blur",
        },
      ],
      password: [
        { required: true, message: "请输入密码", trigger: "blur" },
        {
          pattern: /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{6,20}$/,
          message: "密码格式不正确！至少包含一个字母、一个数字，长度为 6-20",
          trigger: "blur",
        },
      ],
      confirmPassword: [
        { required: true, message: "请确认密码", trigger: "blur" },
        {
          validator: (rule, value, callback) => {
            // 确认密码与密码一致校验
            if (value !== registerForm.password) {
              callback(new Error("两次输入的密码不一致！"));
            } else {
              callback();
            }
          },
          trigger: "blur",
        },
      ],
    };

    // 登录表单引用
    const loginRef = ref(null);
    // 注册表单引用
    const registerRef = ref(null);

    // 提交表单处理函数
    const handleSubmit = async (formRef) => {
      if (currentForm.value === "login") {
        await loginRef.value.validate(async (valid) => {
          // 验证登录表单
          if (valid) {
            ElMessage.success("登录成功！");
            //登录成功，跳到首页
            router.push('/');
            store.isLogin = true;
            store.setUser(loginForm.username, 'admin');

            
          } else {
            ElMessage.error("表单验证失败，请检查输入！");
          }
        });
      } else {
        await registerRef.value.validate(async (valid) => {
          // 验证注册表单
          if (valid) {
            ElMessage.success("注册成功！");
            //注册成功，跳到登录
            currentForm.value = "login";
            loginRef.value.resetFields();  // 重置登录表单
          } else {
            ElMessage.error("表单验证失败，请检查输入！");
          }
        });
      }
    };

    // 切换表单类型函数
    const toggleForm = (formType) => {
      currentForm.value = formType;
    };

    // 处理忘记密码事件（暂未实现）
    const handleForgotPassword = () => {
      ElMessage.warning("忘记密码功能尚未实现！");
    };



</script>

<template>
  <div class="login-register">
    <!-- 左侧面板，包含网站LOGO -->
    <div class="left-panel">
      <img src="../assets/images/hut.png" alt="Logo" class="logo" />
    </div>
    <!-- 右侧面板，包含登录和注册表单 -->
    <div class="right-panel">
      <el-card class="form-card">
        <!-- 根据当前表单状态显示不同的标题 -->
        <h2 v-if="currentForm === 'login'">登录</h2>
        <h2 v-else>注册</h2>

        <!-- 登录表单 -->
        <el-form
          :model="loginForm"
          :rules="loginRules"
          ref="loginRef"
          v-if="currentForm === 'login'"
        >
          <!-- 用户名输入框 -->
          <el-form-item prop="username">
            <el-input
              v-model="loginForm.username"
              placeholder="请输入用户名"
              prefix-icon="el-icon-user"
            ></el-input>
          </el-form-item>
          <!-- 密码输入框 -->
          <el-form-item prop="password">
            <el-input
              v-model="loginForm.password"
              type="password"
              placeholder="请输入密码"
              prefix-icon="el-icon-lock"
            ></el-input>
          </el-form-item>
          
          <!-- 记住我复选框和忘记密码链接 -->
          <el-form-item class="remember-me">
            <el-checkbox v-model="rememberMe">记住我</el-checkbox>
            <a href="#" class="forgot-password" @click="handleForgotPassword"
              >忘记密码？</a
            >
          </el-form-item>
          <!-- 登录按钮 -->
          <el-form-item>
            <el-button
              type="primary"
              class="login-btn"
              @click="handleSubmit(loginRef)"
              >登 录</el-button
            >
          </el-form-item>
        </el-form>

        <!-- 注册表单 -->
        <el-form
          :model="registerForm"
          :rules="registerRules"
          ref="registerRef"
          v-else
        >
          <!-- 用户名输入框 -->
          <el-form-item prop="username">
            <el-input
              v-model="registerForm.username"
              placeholder="请输入用户名"
              prefix-icon="el-icon-user"
            ></el-input>
          </el-form-item>
          <!-- 密码输入框 -->
          <el-form-item prop="password">
            <el-input
              v-model="registerForm.password"
              type="password"
              placeholder="请输入密码"
              prefix-icon="el-icon-lock"
            ></el-input>
          </el-form-item>
          <!-- 确认密码输入框 -->
          <el-form-item prop="confirmPassword">
            <el-input
              v-model="registerForm.confirmPassword"
              type="password"
              placeholder="请确认密码"
              prefix-icon="el-icon-lock"
            ></el-input>
          </el-form-item>
          
          <!-- 注册按钮 -->
          <el-form-item>
            <el-button
              type="primary"
              class="login-btn"
              @click="handleSubmit(registerRef)"
              >注 册</el-button
            >
          </el-form-item>
        </el-form>

        <!-- 切换登录/注册表单链接 -->
        <div class="switch-form">
          <a href="#" v-if="currentForm === 'login'" @click="toggleForm('register')"
            >注册 →</a
          >
          <a href="#" v-else @click="toggleForm('login')"
            >返回登录</a
          >
        </div>
      </el-card>
    </div>
  </div>
</template>



<style scoped lang="less">
/* 整体布局样式 */
.login-register {
  display: flex;
  height: 100vh;
  width: 100%;
  // background-color: #2196f3;
}

/* 左侧面板样式 */
.left-panel {
  width: 50%;
  
  display: flex;
  align-items: center;
  justify-content: center;
  img{
    width: 50%;
  }
}

/* 右侧面板样式 */
.right-panel {
  width: 50%;
  background-color: white;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* LOGO样式 */
.logo {
  width: 200px;
}

/* 表单卡片样式 */
.form-card {
  width: 500px;
  padding: 20px;
  h2{
    margin-bottom: 10px;
  }
  .remember-me {
    display: flex;
    justify-content: space-between;
  } 
}

/* 表单卡片圆角设置 */
.el-card {
  border-radius: 12px;
}

/* 表单项间距 */
.el-form-item {
  margin-bottom: 20px;
}

/* 登录按钮宽度设置为100% */
.login-btn {
  width: 100%;
}

/* 切换表单链接样式 */
.switch-form {
  margin-top: 15px;
  text-align: center;
}

/* 忘记密码链接样式 */
.forgot-password {
  float: right;
  color: #2196f3;
  margin-left: 250px;
}

/* 忘记密码链接鼠标悬停样式 */
.forgot-password:hover {
  text-decoration: underline;
}
</style>