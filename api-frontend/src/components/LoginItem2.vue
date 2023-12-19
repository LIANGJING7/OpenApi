<template>
  <div class="nav">
    <el-menu
      :default-active="activeIndex"
      class="el-menu-demo"
      mode="horizontal"
      style="display: flex;   justify-content: center; font-size: 14px"
    >
      <el-menu-item index="1">注册账号</el-menu-item>
    </el-menu>
  </div>
  <div class="box">
    <LoginItem2Son1></LoginItem2Son1>
  </div>
</template>

<script setup>

import LoginItem2Son1 from '@/components/LoginItem2Son1.vue'

</script>

<style scoped lang="less">
.box{
  margin: auto;
  width: 293px;
  height: 220px;
}
.nav{
  width: 293px;
  margin-bottom: 16px;
}
</style>
