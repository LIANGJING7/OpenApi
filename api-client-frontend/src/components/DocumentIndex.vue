<template>
  <div class="bd">
    <div class="header">
      <span>查看接口文档</span>
    </div>
    <div class="bottom">
      <div class="center-item1">
        <div class="center-item1-top">
          <span>{{ invokeData.name }}</span>
          <span style="margin-right: 57px;">
              <a-button type="primary" @click="getCount()">获 取</a-button>
          </span>
        </div>
        <div class="center-item1-bottom">
          <span>接口状态: {{ invokeData.status}}</span>
          <span>描述: {{ invokeData.description}}</span>
          <span>剩余请求次数: {{ invokeData.leftNum}}次</span>
          <span>请求地址: {{ invokeData.url}}</span>
          <span>请求方法: {{ invokeData.method}}</span>
          <span>请求参数: {{ invokeData.params}}</span>
          <span>请求头: {{ invokeData.requestHeader}}</span>
          <span>响应头: {{ invokeData.responseHeader}}</span>
        </div>
      </div>
      <div class="center-item2">
        <div class="top">在线测试</div>
        <div class="center-item2-center">
          <a-textarea v-model:value="newParams"  :rows="4"/>
          <br/>
        </div>
        <div class="btn">
          <a-button type="primary" @click="interfaceInvoke()">发送</a-button>
        </div>
      </div>
      <div class="center-item3">
        <div class="center-item3-top">测试结果</div>
        <div class="center-item3-bottom">{{val}}</div>

      </div>
      </div>

    </div>
</template>
<script setup>
import { onMounted, reactive, ref } from 'vue'
import * as http from '@/request/http'
import { message } from 'ant-design-vue'
const newParams = ref('')
const invokeData = reactive({
  interfaceInfoId: window.sessionStorage.getItem('interfaceId'),
  userId: window.sessionStorage.getItem('userId'),
  name: window.sessionStorage.getItem('name'),
  url: window.sessionStorage.getItem('url'),
  status: window.sessionStorage.getItem('status'),
  description: window.sessionStorage.getItem('description'),
  method: window.sessionStorage.getItem('method'),
  params: window.sessionStorage.getItem('params'),
  requestHeader: window.sessionStorage.getItem('requestHeader'),
  responseHeader: window.sessionStorage.getItem('responseHeader'),
  accessKey: window.sessionStorage.getItem('accessKey'),
  secretKey: window.sessionStorage.getItem('secretKey'),
  leftNum: 0
})
const val = ref('')
onMounted(() => {
  http.get('/userInterfaceInfo/getLeftNum', invokeData).then((res) => {
    console.log(res)
    if (res.data.code === 0) {
      invokeData.leftNum = res.data.data
    }
  }).catch(() => {
    info('次数获取失败')
  })
})
function interfaceInvoke () {
  invokeData.params = newParams.value
  http.postSendJson('/interfaceInfo/invoke', invokeData).then((res) => {
    console.log(res)
    if (res.data.code === 0) {
      info('接口调用成功')
      val.value = res.data.data
      invokeData.leftNum = invokeData.leftNum - 1
      console.log(invokeData.val)
    } else {
      info('接口调用失败')
    }
  }).catch(() => {
    info('接口调用失败')
  })
}
function getCount () {
  http.postSendJson('/userInterfaceInfo/getCount', invokeData).then((res) => {
    console.log(res)
    if (res.data.code === 0) {
      info('获取次数成功')
      val.value = res.data.data
      window.location.reload()
    } else {
      info('获取次数失败')
    }
  }).catch(() => {
    info('获取次数失败')
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
  padding-left: 20px;
  background-color: white;
  padding-bottom: 32px;

  .center-item1-top {
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 64px;
    padding-right: 15px;
    font-size: 18px;
    font-weight: 500;
    width: 100%;

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
  height: 225px;
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

<!--<template>-->
<!--  <div class="bd">-->
<!--    <div class="header">-->
<!--      <span>查看接口文档</span>-->
<!--    </div>-->
<!--    <div class="bottom">-->
<!--      <div class="center-item1">-->
<!--        <div class="center-item1-top">-->
<!--          <span>hahaha</span>-->
<!--          <span>-->

<!--  <div>-->
<!--    <a-button type="primary" @click="showModal">购 买</a-button>-->
<!--    <a-modal v-model:open="open" title="购买接口" @ok="handleOk" width="600px">-->
<!--    <div class="modal-content">-->
<!--      <div class="top">getname</div>-->
<!--      <div class="center">-->
<!--          <el-table :data="tableData" style="width: 100%">-->
<!--          <el-table-column prop="date" label="Date" width="180"/>-->
<!--          <el-table-column prop="name" label="Name" width="180"/>-->
<!--          <el-table-column prop="address" label="Address"/>-->
<!--          </el-table>-->
<!--           <el-table :data="tableData" style="width: 100%">-->
<!--          <el-table-column prop="date" label="Date" width="180"/>-->
<!--          <el-table-column prop="name" label="Name" width="180"/>-->
<!--          <el-table-column prop="address" label="Address"/>-->
<!--          </el-table>-->
<!--      </div>-->
<!--      <div class="bottom"></div>-->
<!--    </div>-->
<!--    </a-modal>-->
<!--  </div>-->
<!--          </span>-->
<!--        </div>-->
<!--        <div class="center-item1-bottom">-->
<!--          <span>接口状态: {{ val }}</span>-->
<!--          <span>描述: {{}}</span>-->
<!--        </div>-->
<!--      </div>-->
<!--      <div class="center-item2">-->
<!--        <div class="top">在线测试</div>-->
<!--        <div class="center-item2-center">-->
<!--          <a-textarea v-model:value="val" placeholder="Basic usage" :rows="4"/>-->
<!--          <br/>-->
<!--        </div>-->
<!--        <div class="btn">-->
<!--          <a-button type="primary" @click="dianji()">发送</a-button>-->
<!--        </div>-->
<!--      </div>-->
<!--      <div class="center-item3">-->
<!--        <div class="center-item3-top">测试结果</div>-->
<!--        <div class="center-item3-bottom">xixi</div>-->

<!--      </div>-->
<!--    </div>-->

<!--  </div>-->

<!--</template>-->
<!--<script setup>-->
<!--import { ref } from 'vue'-->

<!--const open = ref(false)-->
<!--const showModal = () => {-->
<!--  open.value = true-->
<!--}-->
<!--const handleOk = e => {-->
<!--  console.log(e)-->
<!--  open.value = false-->
<!--}-->
<!--const val = ref('hh')-->

<!--function dianji () {-->
<!--  console.log(val)-->
<!--}-->

<!--const tableData = [-->
<!--  {-->
<!--    date: '2016-05-03',-->
<!--    name: 'Tom',-->
<!--    address: 'No. 189, Grove St, Los Angeles'-->
<!--  }-->
<!--]-->

<!--</script>-->

<!--<style scoped lang="less">-->
<!--.bd {-->
<!--  width: 100%;-->
<!--  height: 100%;-->
<!--  background-color: #fafafa;-->
<!--}-->

<!--.header {-->
<!--  height: 58px;-->
<!--  text-align: left;-->
<!--  padding-top: 10px;-->
<!--  padding-left: 27px;-->
<!--  line-height: 58px;-->

<!--  span {-->
<!--    font-size: 20px;-->
<!--    display: inline-block;-->
<!--    height: 28px;-->
<!--    line-height: 28px;-->
<!--    font-weight: 550;-->
<!--  }-->
<!--}-->

<!--.bottom {-->
<!--  display: flex;-->
<!--  flex-direction: column;-->
<!--  flex-wrap: wrap;-->
<!--  align-items: center;-->
<!--  justify-content: space-between;-->
<!--  padding-top: 20px;-->
<!--  background-color: #fafafa;-->
<!--  width: 100%;-->
<!--}-->

<!--.center-item1 {-->
<!--  width: 96%;-->
<!--  height: 400px;-->
<!--  padding-left: 20px;-->
<!--  background-color: white;-->

<!--  .center-item1-top {-->
<!--    padding-right: 15px;-->
<!--    font-size: 18px;-->
<!--    font-weight: 500;-->
<!--    height: 60px;-->
<!--    width: 100%;-->
<!--    display: flex;-->
<!--    justify-content: space-between;-->
<!--    align-items: center;-->
<!--  }-->

<!--  .center-item1-bottom {-->
<!--    padding-top: 10px;-->
<!--    display: flex;-->
<!--    align-items: flex-start;-->
<!--    flex-direction: column;-->
<!--    flex-wrap: wrap;-->
<!--    justify-content: space-between;-->
<!--    width: 100%;-->
<!--    height: 340px;-->
<!--  }-->
<!--}-->

<!--.center-item2 {-->
<!--  margin-top: 20px;-->
<!--  padding-left: 20px;-->
<!--  padding-right: 20px;-->
<!--  width: 96%;-->
<!--  height: 200px;-->
<!--  background-color: white;-->

<!--  .top {-->
<!--    height: 60px;-->
<!--    line-height: 60px;-->
<!--    text-align: left;-->
<!--  }-->

<!--  .btn {-->
<!--    margin-top: 20px;-->
<!--    display: flex;-->
<!--    justify-content: left;-->
<!--  }-->
<!--}-->

<!--.center-item3 {-->
<!--  margin-top: 65px;-->
<!--  margin-bottom: 100px;-->
<!--  width: 96%;-->
<!--  height: 110px;-->
<!--  background-color: white;-->

<!--  .center-item3-top {-->
<!--    height: 60px;-->
<!--    padding-left: 20px;-->
<!--    padding-right: 20px;-->
<!--    line-height: 60px;-->
<!--    text-align: left;-->
<!--  }-->

<!--  .center-item3-bottom {-->
<!--    border-top: darkgray solid 0.6px;-->
<!--    padding-left: 20px;-->
<!--    text-align: left;-->
<!--    height: 50px;-->
<!--    line-height: 50px;-->
<!--  }-->
<!--}-->

<!--.modal-content {-->
<!--  width: 100%;-->
<!--  height: 400px;-->

<!--  border-top: darkgray solid 0.8px;-->

<!--  .top {-->
<!--    font-size: 17px;-->
<!--    font-weight: 550;-->
<!--    line-height: 60px;-->
<!--    height: 60px;-->
<!--    text-align: left;-->
<!--  }-->
<!--  .bottom{-->
<!--    font-size: 15px;-->
<!--    font-weight: 550;-->
<!--    line-height: 60px;-->
<!--    height: 60px;-->
<!--    text-align: left;-->
<!--  }-->
<!--}-->
<!--</style>-->
