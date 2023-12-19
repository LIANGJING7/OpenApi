<template>
  <div class="nav">
    <el-menu
      :default-active="activeIndex"
      class="el-menu-demo"
      mode="horizontal"
      style="display: flex;   justify-content: center; font-size: 14px"
    >
      <el-menu-item index="1" @click="dian(1)">账号密码登录</el-menu-item>
    </el-menu>
  </div>
  <div class="box">
    <LoginItemSon1 v-if="flag === 1 "></LoginItemSon1>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import LoginItemSon1 from './LoginItemSon1.vue'

const activeIndex = ref('1')

const flag = ref(1)
function dian (val) {
  flag.value = val
}
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
