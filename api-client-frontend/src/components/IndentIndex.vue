<template>
  <div class="header">
    <span>我的订单</span>
  </div>
  <div class="center">
    <el-menu
      :default-active="activeIndex"
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
    >
      <el-menu-item index="1" style="padding-left: 0;margin-right: 5px;background-color: white">待支付</el-menu-item>
      <el-menu-item index="2" style="padding-left: 0 ;margin-right: 5px;background-color: white">已支付</el-menu-item>
      <el-menu-item index="3" style="padding-left: 0 ;margin-right: 5px;background-color: white">已过期/失效</el-menu-item>
    </el-menu>
  </div>
  <div class="bottom">
    <a-table :data-source="data" style="font-weight: 0 ;font-size: 13px">
      <a-table-column key="name"  title="订单号" data-index="name" style="text-align: center "/>
      <a-table-column key="name"   title="接口名" data-index="expalm" />
      <a-table-column key="name"   title="接口描述" data-index="expalm" />
      <a-table-column key="name"   title="状态" data-index="expalm" />
      <a-table-column key="name"   title="数量" data-index="expalm" />
      <a-table-column key="name"   title="总价" data-index="expalm" />
      <a-table-column key="name"   title="创建时间" data-index="expalm" />
      <a-table-column key="action"  title="操作" >
        <template #default="">
        <span>
        <a-button  @click="showModal" style="font-size: 12px">查看详情</a-button>
       <a-modal v-model:open="open" title="订单详情" @ok="handleOk" width="600px">
       <div class="modal-content">
       <div class="modal-content-top">getname</div>
       <div class="modal-content-center">
          <el-table :data="data" style="width: 100%" >
          <el-table-column prop="name" label="数据" width="180"/>
          </el-table>
           <el-table :data="data" style="width: 100%">
          <el-table-column prop="expalm" label="Date" width="180"/>
          </el-table>
      </div>
      <div class="modal-content-bottom">总计: </div>
    </div>
    </a-modal>
        </span>
        </template>
      </a-table-column>
    </a-table>
  </div>
</template>
<script setup>
import { ref } from 'vue'

const open = ref(false)
const data = [
  {
    key: '1',
    name: '32',
    expalm: '随机获取'
  },
  {
    key: '2',
    name: 42

  },
  {
    key: '3',
    name: 32
  }
]

const showModal = () => {
  open.value = true
}
const handleOk = e => {
  console.log(e)
  open.value = false
}
</script>

<style scoped lang="less">
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
  padding-top: 18px;
  margin: auto;
  width: 96%;

}
.center{
  margin: auto;
  width: 96%;
}
.modal-content-top{
  font-size: 17px;
  font-weight: 550;
  line-height: 60px;
  height: 60px;
  text-align: left;
}
.modal-content-center{
  padding-top: 20px;
  width: 96%;
  margin: auto;
}
.modal-content-bottom{
  font-size: 15px;
  font-weight: 550;
  line-height: 60px;
  height: 60px;
  text-align: left;
}
</style>
