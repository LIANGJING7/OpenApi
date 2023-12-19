<template>
  <div class="header">
    <span>我拥有的接口</span>
  </div>
 <div class="bottom">
   <a-table :columns="columns" :data-source="personInterfaceInfoData" bordered>
   </a-table>
 </div>
</template>
<script setup>
import { onMounted, reactive, ref } from 'vue'
import * as http from '@/request/http'
import { message } from 'ant-design-vue'
const url = 'interfaceInfo/getPersonalInterfaceInfo'
const columns = [
  {
    title: 'name',
    dataIndex: 'name'
  },
  {
    title: 'description',
    dataIndex: 'description'
  },
  {
    title: 'url',
    dataIndex: 'url'
  },
  {
    title: 'method',
    dataIndex: 'method'
  },
  {
    title: 'status',
    dataIndex: 'status'
  },
  {
    title: 'totalNum',
    dataIndex: 'totalNum'
  },
  {
    title: 'leftNum',
    dataIndex: 'leftNum'
  }
]
const invokeData = reactive({
  id: window.sessionStorage.getItem('userId')
})
const personInterfaceInfoData = ref([{
  id: 0,
  name: '',
  description: '',
  url: '',
  method: '',
  status: '',
  totalNum: 0,
  leftNum: 0
}])

onMounted(() => {
  console.log(invokeData)
  http.get(url, invokeData).then((res) => {
    if (res.data.code === 0) {
      personInterfaceInfoData.value = res.data.data
    } else {
      info('暂时没有接口哦')
    }
  }).catch(() => {
    info('暂时没有接口哦')
  })
})

const info = (msg) => {
  message.info(msg)
}
</script>
<style  scoped lang="less">
.header{
  height: 58px;
  text-align: left;
  padding-top: 10px;
  padding-left: 24px;
  line-height: 58px;
  margin-bottom: 30px;
  span{
    font-size: 20px;
    display: inline-block;
    height: 28px;
    line-height: 28px;
    font-weight: 550;
  }
}
.bottom{
  width: 96%;
  margin: auto;
}
.center{
  .center-top {
    font-size: 17px;
    font-weight: 550;
    line-height: 60px;
    height: 60px;
    text-align: left;
  }
  .center-bottom{
    font-size: 15px;
    font-weight: 550;
    line-height: 60px;
    height: 60px;
    text-align: left;
  }
}
.btn{
  display: flex;
  width: 96%;
  height: 49px;
  font-size: 18px;
  line-height: 34px;
  font-weight: 500;
  justify-content: space-between;
}

</style>
<script setup>
</script>
