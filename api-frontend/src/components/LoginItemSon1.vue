<template>
  <a-form
    :model="formState"
    name="normal_login"
    class="login-form"
  >
    <a-form-item
      label=""
      name="username"
    >
      <a-input v-model:value="formState.userAccount">
        <template #prefix>
          <UserOutlined class="site-form-item-icon">
            <img src="/yonghu.png" alt="" class="com">
          </UserOutlined>
        </template>
      </a-input>
    </a-form-item>

    <a-form-item
      label=""
      name="password"
    >
      <a-input-password v-model:value="formState.userPassword" >
        <template #prefix>
          <LockOutlined class="site-form-item-icon">
            <img src="/suo.png" alt="" class="com">
          </LockOutlined>
        </template>
      </a-input-password>
    </a-form-item>
    <a-form-item class="foooter">
      <a-form-item name="remember" no-style>
        <a-checkbox v-model:checked="formState.remember">记住密码</a-checkbox>
      </a-form-item>
      <a class="login-form-forgot" href=""><router-link to='/log'>没有账号?去注册</router-link></a>
    </a-form-item>

    <a-form-item>
      <a-button style="width: 293px" @click="login">
        登录
      </a-button>
    </a-form-item>
  </a-form>
</template>
<script>
import * as http from '@/request/http'
import { message } from 'ant-design-vue'

const url = '/user/login'
export default {
  name: 'LoginItemSon1',
  data () {
    return {
      formState: {
        id: 0,
        userAccount: window.sessionStorage.getItem('userAccount'),
        userPassword: window.sessionStorage.getItem('userPassword'),
        remember: false
      }
    }
  },
  methods: {
    login () {
      console.log("'--------------'")
      http.postSendJson(url, this.formState).then(res => {
        if (res.data.code === 0 && res.data.message === 'ok') {
          if (this.formState.remember === false) {
            res.data.data.userPassword = ''
          }
          if (this.formState.remember === true) {
            res.data.data.userPassword = this.formState.userPassword
          }
          this.formState.id = res.data.data.id
          window.sessionStorage.setItem('id', res.data.data.id)
          window.sessionStorage.setItem('userAccount', res.data.data.userAccount)
          window.sessionStorage.setItem('userPassword', res.data.data.userPassword)
          window.sessionStorage.setItem('secretKey', res.data.data.secretKey)
          window.sessionStorage.setItem('accessKey', res.data.data.accessKey)
          this.$router.push('/index')
        } else {
          this.info(res.data.message)
        }
      }).catch(e => {
        message.info('登录失败')
        this.$router.push('/')
      }
      )
    },
    info (msg) {
      message.info('登录失败' + ':' + msg)
    }
  }
}
</script>
<style scoped lang="less">
#components-form-demo-normal-login .login-form {
  max-width: 300px;
}
#components-form-demo-normal-login .login-form-forgot {
  float: right;
}
#components-form-demo-normal-login .login-form-button {
  width: 100%;
}
.com{
  width: 20px;
  height: 20px;
}

.foooter{
  display: flex;
  justify-content: space-between;
  width: 293px;
}
</style>
