<template>
  <div class="header">
    <span>主页</span>
  </div>
  <div class="bottom">

      <a-table :data-source="data"  class="bcg" >
        <a-table-column key="name"  title="INTERFACE" data-index="name" maxWidth="900px" width="80%"/>
        <a-table-column key="description"   data-index="expalm"  maxWidth="300px" width="10%"/>
        <a-table-column key="action"  style="background-color: snow;" >
          <template #default="{ record }">
        <span @click="dianji(record)">
          <a-divider type="vertical" />
          <a>查看文档</a>
        </span>
          </template>
        </a-table-column>
      </a-table>
  </div>
</template>
<script setup>
import router from '@/router'
import { onMounted, ref } from 'vue'
import * as http from '@/request/http'
import { message } from 'ant-design-vue'
const data = ref([
  {
    id: '1',
    name: '32',
    description: '',
    method: '',
    requestHeader: '',
    responseHeader: '',
    status: '',
    params: '',
    url: ''
  }
])
onMounted(() => {
  http.get('/interfaceInfo/list').then((res) => {
    if (res.data.code === 0) {
      console.log(res)
      data.value = res.data.data
    } else {
      info('数据获取失败')
    }
  }).catch(() => {
    info('数据获取失败')
  })
})
const info = (msg) => {
  message.info(msg)
}
function dianji (interfaceInfo) {
  window.sessionStorage.setItem('interfaceId', interfaceInfo.id)
  window.sessionStorage.setItem('name', interfaceInfo.name)
  window.sessionStorage.setItem('status', interfaceInfo.status)
  window.sessionStorage.setItem('description', interfaceInfo.description)
  window.sessionStorage.setItem('url', interfaceInfo.url)
  window.sessionStorage.setItem('method', interfaceInfo.method)
  window.sessionStorage.setItem('params', interfaceInfo.params)
  window.sessionStorage.setItem('requestHeader', interfaceInfo.requestHeader)
  window.sessionStorage.setItem('responseHeader', interfaceInfo.responseHeader)
  const url = '/api/documentIndex'
  router.push(url)
}

</script>

<style scoped lang="less">
.header{
  height: 58px;
  text-align: left;
  padding-top: 10px;
  padding-left: 27px;
  line-height: 58px;
  span{
    font-size: 20px;
    display: inline-block;
    height: 28px;
    line-height: 28px;
    font-weight: 550;
  }
}
.bottom{
  padding-left: 20px;
}
.bcg{
  background-color: #fafafa;
}
.fontsize{
  font-size: 12px;
}
.marg{
 border-left: none;
}
</style>
