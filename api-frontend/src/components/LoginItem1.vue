<template>
  <div class="nav">
    <el-menu
      :default-active="activeIndex"
      class="el-menu-demo"
      mode="horizontal"
      style="display: flex;   justify-content: center; font-size: 14px"
    >
      <el-menu-item>账号密码登录</el-menu-item>
    </el-menu>
  </div>
  <div class="box">
    <LoginItemSon1></LoginItemSon1>
  </div>
</template>

<script setup>
import LoginItemSon1 from '@/components/LoginItemSon1.vue'

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
