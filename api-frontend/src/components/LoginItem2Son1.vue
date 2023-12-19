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
    <a-form-item
      label=""
      name="password"
    >
      <a-input-password v-model:value="formState.checkPassword">
        <template #prefix>
          <LockOutlined class="site-form-item-icon">
            <img src="/suo.png" alt="" class="com">
          </LockOutlined>
        </template>
      </a-input-password>
    </a-form-item>
    <a-form-item class="foooter">
      <a class="login-form-forgot" href=""><router-link to="/loginItem">已有账号?去登陆</router-link></a>
    </a-form-item>

    <a-form-item>
      <a-button style="width: 293px" @click="register">
        注册
      </a-button>
    </a-form-item>
  </a-form>
</template>
<script>
import * as http from '@/request/http'
import { message } from 'ant-design-vue'

const url = '/user/register'
export default {
  name: 'LoginItem2Son1',
  data () {
    return {
      formState: {
        id: 0,
        userAccount: '',
        userPassword: '',
        checkPassword: '',
        remember: true
      },
      msg: ''
    }
  },
  methods: {
    register () {
      http.postSendJson(url, this.formState).then(res => {
        console.log(res)
        if (res.data.code === 0) {
          window.sessionStorage.setItem('id', res.data.data.id)
          window.sessionStorage.setItem('userAccount', res.data.data.userAccount)
          window.sessionStorage.setItem('userPassword', res.data.data.userPassword)
          window.sessionStorage.setItem('secretKey', res.data.data.secretKey)
          window.sessionStorage.setItem('accessKey', res.data.data.accessKey)
          this.$router.push('/loginItem')
        } else {
          this.info(res.data.message)
        }
      }).catch(e => {
      })
    },
    info (msg) {
      message.info('注册失败' + ':' + msg)
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
