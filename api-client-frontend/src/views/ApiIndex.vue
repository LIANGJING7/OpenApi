<template>
  <a-layout class="layout">
    <a-layout-header style="height: 54px;background-color: white; padding: 0" >
      <el-menu
        class="el-menu-demo"
        mode="horizontal"
        style=" font-size: 14px"
      >
        <div class="header-top">
          <img src="/API.png" alt="">
          <span>JING API</span>
        </div>
        <el-menu-item index="1" style="opacity: 0.8">  <router-link to="/api/homePage">主页</router-link></el-menu-item>

<!--        <el-menu-item index="2" style="width: 100px;opacity: 0.8">  <router-link to="/api/indentIndex">订单</router-link></el-menu-item>-->

        <el-menu-item index="3" style="width: 100px;opacity: 0.8">   <router-link to="/api/interfaceIndex">我的接口</router-link></el-menu-item>
      </el-menu>
    </a-layout-header>
    <a-layout-content style="padding: 0 0px">
      <div :style="{ background: '#fff', padding: '0px', minHeight: '541px',height: '541px' }">
        <router-view/>
      </div>
    </a-layout-content>
  </a-layout>
</template>
<script setup>
// import { ref } from 'vue'
// const selectedKeys = ref(['2'])
</script>

<style scoped lang="less">
.site-layout-content {
  min-height: 280px;
  padding: 24px;
  background: #fff;
}
#components-layout-demo-top .logo {
  float: left;
  width: 120px;
  height: 31px;
  margin: 16px 24px 16px 0;
  background: rgba(255, 255, 255, 0.3);
}
.ant-row-rtl #components-layout-demo-top .logo {
  float: right;
  margin: 16px 0 16px 24px;
}

[data-theme='dark'] .site-layout-content {
  background: #141414;
}
.header-top{
  display: flex;
  justify-content: left;
  align-items: center;
  width: 108px;
  height: 60px;
  margin-left: 14px;
  img{
    width: 36px;
    height: 36px;
  }
  span{
    margin-left: 6px;
    font-weight: 600;
    font-size: 14px;
    opacity: 0.9;
  }
}
</style>
