<template>
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
    name="username"
  >
    <a-input-password v-model:value="formState.userPassword">
      <template #prefix>
        <UserOutlined class="site-form-item-icon">
          <img src="/suo.png" alt="" class="com">
        </UserOutlined>
      </template>
    </a-input-password>
  </a-form-item>
    <a-form-item class="foooter">
      <a-form-item name="remember" no-style>
        <a-checkbox v-model:checked="formState.remember">自动登录</a-checkbox>
      </a-form-item>
      <a class="login-form-forgot" href=""><router-link to="/LoginItem2">没有账号?去注册</router-link></a>
    </a-form-item>

    <a-form-item>
      <a-button style="width: 293px" @click="login">
        登录
      </a-button>
    </a-form-item>
</template>
<script>
import * as http from '@/request/http'
import { message } from 'ant-design-vue'

export default {
  name: 'LoginItemSon1',
  data () {
    return {
      formState: {
        userAccount: '',
        userPassword: '',
        secretKey: '',
        accessKey: '',
        remember: true
      }
    }
  },
  methods: {
    onFinish (values) {
      console.log('Success:', values)
    },
    onFinishFailed (errorInfo) {
      console.log('Failed:', errorInfo)
    },
    login () {
      http.postSendJson('/user/login', this.formState).then((res) => {
        if (res.data.code === 0) {
          this.info("'登录成功'")
          window.sessionStorage.setItem('userId', res.data.data.id)
          window.sessionStorage.setItem('userAccount', res.data.data.userAccount)
          window.sessionStorage.setItem('userPassword', res.data.data.userPassword)
          window.sessionStorage.setItem('secretKey', res.data.data.secretKey)
          window.sessionStorage.setItem('accessKey', res.data.data.accessKey)
          this.$router.push('/api')
        } else {
          this.info("'登录失败'")
        }
      }).catch(() => {
        this.info("'登录失败'")
      })
    },
    info (msg) {
      message.info(msg)
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
