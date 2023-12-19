<template>
  <div class="header">
    <h4>在线接口开放平台</h4>
  </div>
  <div class="bottom">
    <el-card class="box-card">
        <div v-for="o in tableData" :key="o" class="text item">
          <span class="item-span">
           <i>   {{o.name }}</i>
            <i style="color: darkgray;font-size: 12px;margin-top: 8px;text-align: left">  {{o.description }}</i>
          </span>
          <span>
          <div>
        <a-button @click="skip(o)">查 看</a-button>
          </div>
          </span>
        </div>
    </el-card>
  </div>
</template>
<script setup>

import router from '@/router'
import { onMounted, ref } from 'vue'
import * as http from '@/request/http'

const tableData = ref([
  {
    name: '无',
    status: 0,
    description: '无',
    url: '无',
    method: '无',
    params: '无',
    requestHeader: '无',
    responseHeader: '无'

  }
])
onMounted(() => {
  http.get('/interfaceInfo/list').then((res) => {
    tableData.value = res.data.data
  })
})
function skip (interfaceInfo) {
  window.sessionStorage.setItem('name', interfaceInfo.name)
  window.sessionStorage.setItem('status', interfaceInfo.status)
  window.sessionStorage.setItem('description', interfaceInfo.description)
  window.sessionStorage.setItem('url', interfaceInfo.url)
  window.sessionStorage.setItem('method', interfaceInfo.method)
  window.sessionStorage.setItem('params', interfaceInfo.params)
  window.sessionStorage.setItem('requestHeader', interfaceInfo.requestHeader)
  window.sessionStorage.setItem('responseHeader', interfaceInfo.responseHeader)
  router.push('/doucmentIndex')
}
</script>
<style scoped lang="less">
.header{
  display: flex;
  padding-left: 30px;
  height: 89px;
  justify-content: left;
  align-items: center;
  font-size: 24px;
  h4{
    font-weight: 550;
    opacity: 0.7;
  }
}
.bottom{
  width: 96%;
  margin: auto;
  background-color: white;
}
.item {
  margin-bottom: 18px;
  display: flex;
  justify-content:space-between;
  align-items: center;
  height: 100px;
  border-bottom: darkgray solid 0.4px;
   .item-span {
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  justify-content: left;
}
}

.box-card {
  width: 100%;
  height: 100%;
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
}
</style>
