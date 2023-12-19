<template>
  <div class="bd">
    <div class="header">
      <span>查看接口文档</span>
    </div>
    <div class="bottom">
      <div class="center-item1">
        <div class="center-item1-top">
          <span>{{  invokeData.name }}</span>
        </div>
        <div class="center-item1-bottom">
          <span>接口状态: {{ invokeData.status}}</span>
          <span>描述: {{ invokeData.description}}</span>
          <span>请求地址: {{ invokeData.url}}</span>
          <span>请求方法: {{ invokeData.method}}</span>
          <span>请求参数: {{ invokeData.params}}</span>
          <span>请求头: {{ invokeData.requestHeader}}</span>
          <span>响应头: {{ invokeData.responseHeader}}</span>
        </div>
      </div>
      <div class="center-item2">
        <div class="top">在线测试</div>
        <div class="center-item2-center" style="display: flex; justify-content: left">
          <input type="text" v-model="params" style="width: 700px; height: 100px;border: darkgray solid 0.5px ">
          <br/>
        </div>
        <div class="btn">
          <a-button type="primary" @click="dianji()">发送</a-button>
        </div>
      </div>
      <div class="center-item3">
        <div class="center-item3-top">测试结果</div>
        <div class="center-item3-bottom">{{result}}</div>
      </div>
    </div>

  </div>

</template>
<script setup>
import { reactive, ref } from 'vue'
import * as http from '@/request/http'
import { message } from 'ant-design-vue'

const postUrl = '/interfaceInfo/invoke'
const result = ref('')
const params = ref('')
const invokeData = reactive({
  name: window.sessionStorage.getItem('name'),
  url: window.sessionStorage.getItem('url'),
  status: window.sessionStorage.getItem('status'),
  description: window.sessionStorage.getItem('description'),
  method: window.sessionStorage.getItem('method'),
  params: window.sessionStorage.getItem('params'),
  requestHeader: window.sessionStorage.getItem('requestHeader'),
  responseHeader: window.sessionStorage.getItem('responseHeader'),
  accessKey: window.sessionStorage.getItem('accessKey'),
  secretKey: window.sessionStorage.getItem('secretKey')
})
function dianji () {
  invokeData.params = params.value
  http.postSendJson(postUrl, invokeData).then((res) => {
    if (res.data.code === 0) {
      console.log(res)
      result.value = res.data.data
    } else {
      info('查询数据失败')
    }
  }).catch(() => {
    info('查询数据失败')
  })
}
const info = (msg) => {
  message.info(msg)
}

</script>

<style scoped lang="less">
.bd {
  width: 100%;
  height: 100%;
  background-color: #fafafa;
}

.header {
  height: 58px;
  text-align: left;
  padding-top: 10px;
  padding-left: 27px;
  line-height: 58px;

  span {
    font-size: 20px;
    display: inline-block;
    height: 28px;
    line-height: 28px;
    font-weight: 550;
  }
}

.bottom {
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  align-items: center;
  justify-content: space-between;
  padding-top: 20px;
  background-color: #fafafa;
  width: 100%;
}

.center-item1 {
  width: 96%;
  height: 400px;
  padding-left: 20px;
  background-color: white;

  .center-item1-top {
    padding-right: 15px;
    font-size: 18px;
    font-weight: 500;
    height: 60px;
    width: 100%;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .center-item1-bottom {
    padding-top: 10px;
    display: flex;
    align-items: flex-start;
    flex-direction: column;
    flex-wrap: wrap;
    justify-content: space-between;
    width: 100%;
    height: 340px;
  }
}

.center-item2 {
  margin-top: 20px;
  padding-left: 20px;
  padding-right: 20px;
  width: 96%;
  height: 200px;
  background-color: white;

  .top {
    height: 60px;
    line-height: 60px;
    text-align: left;
  }

  .btn {
    margin-top: 20px;
    display: flex;
    justify-content: left;
  }
}

.center-item3 {
  margin-top: 65px;
  margin-bottom: 100px;
  width: 96%;
  height: 110px;
  background-color: white;

  .center-item3-top {
    height: 60px;
    padding-left: 20px;
    padding-right: 20px;
    line-height: 60px;
    text-align: left;
  }

  .center-item3-bottom {
    border-top: darkgray solid 0.6px;
    padding-left: 20px;
    text-align: left;
    height: 50px;
    line-height: 50px;
  }
}

.modal-content {
  width: 100%;
  height: 400px;

  border-top: darkgray solid 0.8px;

  .top {
    font-size: 17px;
    font-weight: 550;
    line-height: 60px;
    height: 60px;
    text-align: left;
  }
  .bottom{
    font-size: 15px;
    font-weight: 550;
    line-height: 60px;
    height: 60px;
    text-align: left;
  }
}
</style>
