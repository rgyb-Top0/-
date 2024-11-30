<template>
  <div class="login-mian">
    <!-- logo标签 -->
    <nav id="baseNavigator">
      <a href="/mall" target="_self">
      </a>
    </nav>

    <!-- 登录主体 -->
    <div class="content">
      <div class="contentMain"></div>
      <div class="loginDiv">
        <!--
        <div :class="[isPwdLogin?'loginSwitch':'loginSwitch_two']" id="loginSwitch" @click="changeLoginTyp()"></div>
        <div class="loginMessage">
          <div class="loginMessageMain">
            <div class="poptip-arrow"><em></em><span></span></div>
            <img
                src="../../assets/images/fore/WebsiteImage/scan-safe.png"
            /><span>扫码登录更安全</span>
          </div>
        </div>-->

        <div class="pwdLogin">
          <span class="loginTitle">密码登录</span>
          <el-form class="loginForm">
            <el-form-item class="loginInputDiv">
              <el-input
                :prefix-icon="User"
                type="text"
                v-model="loginForm.userName"
                placeholder="用户名/邮箱/手机号"
              />
            </el-form-item>
            <el-form-item class="loginInputDiv">
              <el-input
                :prefix-icon="Lock"
                type="password"
                placeholder="密码输入"
                show-password
                v-model="loginForm.userPassword"
              />
            </el-form-item>
          </el-form>
          <!-- 登录按钮 -->
          <button class="loginButton" @click="OnSubmit()">登录</button>
          <div class="loginLinks">
            <router-link to="/resetPwd">忘记密码</router-link>
            <router-link to="/register">免费注册</router-link>
          </div>
          <div class="error_message">
            <p id="error_message_p"></p>
          </div>
        </div>

      </div>
    </div>



  </div>
</template>

<script setup lang="ts">
import { Lock, User } from "@element-plus/icons-vue";
import { ElMessage } from "element-plus";
import { useUserStore } from "../../store/modules/user";
const UserStore = useUserStore();

import { useRouter } from "vue-router";
import {LoginParams} from "../../api/user/types";
const router = useRouter();

const loginForm = ref<LoginParams>({
  userName: "",
  userPassword: "",
});
const isPwdLogin = ref<boolean>(true)
const loginPwdDisplay =  ref<string>('block')
const loginCodeDisplay = ref<string>('none')

// 登录方式改变
const changeLoginTyp  = ()=>{
  if(isPwdLogin.value){
    loginPwdDisplay.value = "none"
    loginCodeDisplay.value = "block"
  }else{
    loginPwdDisplay.value = "block"
    loginCodeDisplay.value = "none"
  }
  isPwdLogin.value = !isPwdLogin.value
}
// 提交
const OnSubmit = () => {
  console.log("登录数据：" + JSON.stringify(loginForm.value));
  UserStore.login(loginForm.value)
    .then((res) => {
      if (res.code === 0) {
        ElMessage.success("登录成功");
        UserStore.getInfo();
        router.replace("/mall");
      } else {
        ElMessage.error("登录失败" + res.message);
      }
    })
    .catch((e) => {
      ElMessage.error(e.message);
    });
};
</script>

<!--整体样式-->
<style lang="scss" scoped>
.login-mian {
  width: 100%;
}

#baseNavigator {
  padding: 22px 0;
  width: 1190px;
  height: 44px;
  margin: auto;
}

#baseNavigator img {
  width: 190px;
  margin-top: 8px;
}

#nav {
  width: auto;
  height: 32px;
  font-family: "Microsoft YaHei UI", Tahoma, serif;
  font-size: 12px;
  position: relative !important;
  background: #f2f2f2;
  z-index: 999;
  border-bottom: 1px solid #e5e5e5;
}
</style>

<!--登录尾部样式-->
<style lang="scss" scoped>
/*尾部样式*/
.footer-copyright {
  border-top: 2px solid #fff;
  background: #fff;
  text-align: left;
  min-height: 177px;
}

.footer-copyright > .footer {
  overflow: hidden;
  width: 1190px;
  margin: 0 auto;
  font: 12px/1.5 "Microsoft YaHei UI", arial, "\5b8b\4f53";
}

.footer > p {
  color: #000;
  padding: 5px 0;
  line-height: 25px;
  margin: 0;
}

.footer a {
  color: #000;
  margin-right: 3px;
  text-decoration: none;
}

.footer a:hover {
  text-decoration: underline;
}

.noneStyle {
  display: none;
}

.copyright {
  width: 990px;
  color: #a4a4a4;
  line-height: 25px;
}

.copyright > a {
  color: #999;
  margin-right: 65px;
}

.copyright img {
  position: relative;
  bottom: 2px;
  vertical-align: middle;
  border: 0;
}

.copyright > b {
  font-weight: 400;
  color: #686868;
}

.footertwo > a {
  margin-right: 10px;
}
</style>

<!--登录主体样式-->
<style lang="scss" scoped>
.content {
  position: relative;
  width: 100%;
  min-width: 1190px;
  margin-bottom: 40px;
  background-color: rgb(241, 220, 165);
  font-family: "Microsoft YaHei UI", serif;
}

.content > .contentMain {
  position: relative;
  width: 1190px;
  height: 600px;
  margin: auto;
  background-image: url(../../assets/images/fore/WebsiteImage/loginbz3.jpg);
}

.loginDiv {
  width: 350px;
  height: 400px;
  position: absolute;
  top: 91px;
  right: 425px;
  background: #ffffff;
  color: #404040;
}

.loginSwitch {
  position: absolute;
  width: 52px;
  height: 52px;
  right: 5px;
  top: 12px;
  background-image: url(../../assets/images/fore/WebsiteImage/scan_icon.png);
  cursor: pointer;
  -webkit-background-size: cover;
  background-size: cover;
}

.loginSwitch_two {
  position: absolute;
  width: 52px;
  height: 52px;
  right: 5px;
  top: 12px;
  background-image: url(../../assets/images/fore/WebsiteImage/pc_icon.png);
  cursor: pointer;
  -webkit-background-size: cover;
  background-size: cover;
}

.loginDiv > .loginMessage {
  position: absolute;
  top: 10px;
  right: 58px;
}

.loginMessage > .loginMessageMain {
  border: 1px solid #f3d995;
  height: 16px;
  line-height: 16px;
  background: #fefcee;
  color: #df9c1f;
  font-size: 12px;
  font-weight: 400;
  padding: 5px 20px 5px 15px;
  position: relative;
}

.loginMessageMain > img {
  margin-right: 8px;
  position: relative;
  bottom: 2px;
}

.loginMessageMain > .poptip-arrow {
  position: absolute;
  top: 8px;
  right: 0;
}

.poptip-arrow > em {
  position: absolute;
  width: 0;
  height: 0;
  top: 0;
  left: 1px;
  border-width: 6px 0 6px 6px;
  border-style: solid;
  border-color: rgba(255, 255, 255, 0);
  border-left-color: #f3d995;
}

.poptip-arrow > span {
  border-width: 6px 0 6px 6px;
  position: absolute;
  width: 0;
  height: 0;
  top: 0;
  left: 0;
  border-style: solid;
  border-color: rgba(255, 255, 255, 0);
  border-left-color: #fefcee;
}

.loginDiv > .pwdLogin {
  display: v-bind(loginPwdDisplay);
  padding: 25px 26px 20px;
}

.pwdLogin .loginInputDiv {
  position: relative;
  margin-top: 25px;
}

.loginInputDiv > .loginLabel {
  display: block;
  width: 38px;
  height: 38px;
  line-height: 38px;
  background: #ddd;
  text-align: center;
  position: absolute;
  top: 1px;
  left: 1px;
  font-size: 18px;
}

.loginLabel > i {
  color: #606060;
}

.loginInputDiv > .loginInput {
  color: #9b9b9b;
  width: 240px;
  font-size: 14px;
  line-height: 18px;
  height: 18px;
  padding: 11px 8px 11px 50px;
  border: 1px solid #cbcbcb;
}

.pwdLogin .loginButton {
  width: 300px;
  height: 42px;
  line-height: 42px;
  background-color: #ff0036;
  font-weight: 700;
  font-size: 16px;
  color: #ffffff;
  // cursor: pointer;
  border-radius: 3px;
  border: 0;
  margin-top: 20px;
}

.loginButton:hover {
  background-color: #ff335e;
}

.pwdLogin .loginLinks {
  margin-top: 25px;
  text-align: right;
  font-size: 12px;
}

.loginLinks > a {
  margin-right: 10px;
  color: #6c6c6c;
  text-decoration: none;
}

.loginLinks > a:hover {
  color: #ff0036;
}

.pwdLogin .error_message {
  margin-top: 30px;
  text-align: center;
  font-size: 14px;
}

.error_message > p {
  position: relative;
  color: #c33;
  left: 20px;
  opacity: 0;
  margin: 0;
}

.loginTitle {
  display: block;
  margin-top: 15px;
  height: 18px;
  line-height: 18px;
  font-size: 16px;
  color: #3c3c3c;
  font-weight: 700;
}

.loginDiv > .qrcodeLogin {
  display: v-bind(loginCodeDisplay);
  padding: 25px 26px 20px;
}

.qrcodeLogin > .qrcodeMain {
  position: relative;
  margin-top: 24px;
  height: 140px;
}

.qrcodeMain > #qrcodeA {
  position: absolute;
  left: 80px;
}

.qrcodeMain > #qrcodeB {
  position: absolute;
  opacity: 0;
  right: 12px;
  top: -15px;
}

.qrcodeLogin > .qrcodeFooter {
  width: 188px;
  margin: 24px auto 0;
  overflow: hidden;
}

.qrcodeFooter > img {
  float: left;
  margin-right: 10px;
  padding-top: 5px;
}

.qrcodeFooter > p {
  float: left;
  width: 144px;
  line-height: 18px;
  color: #6c6c6c;
  font-size: 12px;
}

.qrcodeFooter > p > a {
  color: #ff0036;
}

.qrcodeFooter > p > a:hover {
  text-decoration: none;
}

.qrcodeLogin > .loginLinks {
  margin-top: 15px;
  overflow: hidden;
  text-align: right;
  font-size: 12px;
}
</style>
