<template>
  <a-form
    :model="formState"
    name="normal_login"
    class="login-form"
    @finish="onFinish"
    @finishFailed="onFinishFailed"
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
      <a-input-password v-model:value="formState.checkPassword" >
        <template #prefix>
          <LockOutlined class="site-form-item-icon">
            <img src="/suo.png" alt="" class="com">
          </LockOutlined>
        </template>
      </a-input-password>
    </a-form-item>
    <a-form-item class="foooter">
      <a class="login-form-forgot" href=""><router-link to="/LoginItem1">已有账号?去登陆</router-link></a>
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
export default {
  name: 'LoginItem2Son1',
  data () {
    return {
      formState: {
        userAccount: '',
        userPassword: '',
        checkPassword: '',
        remember: false
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
    register () {
      http.postSendJson('/user/register', this.formState).then((res) => {
        console.log('=----' + res)
        if (res.data.code === 0) {
          this.info('注册成功')
          this.$router.push('/')
        } else {
          this.info("'注册失败'")
        }
      }).catch(() => {
        this.info("'注册失败'")
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
