<template>
<div class="box">
<div class="left">
  <el-col :span="12" style="max-width: 100%;">
    <div class="left-top">
      <img src="API.png">
      <h5 class="mb-2">JING 接口</h5>
    </div>
    <el-menu
      default-active="2"
      class="el-menu-vertical-demo"
      style="border-right: none"
    >
        <el-menu-item index="1" >
          <el-icon><img src="smail.png" alt="" class="img-item"></el-icon>
          <span style="font-size: 17px;color: black;opacity: 0.7;font-weight: 550" class="zhuye"><router-link to="/managerItem2">主 页</router-link></span>
        </el-menu-item>

      <el-sub-menu index="2" style="background-color: #fcfcfc">
        <template #title>
          <el-icon><img src="huang.png" alt="" class="img-item"></el-icon>
          <span style="font-size: 17px;color: black;opacity: 0.7;font-weight: 550;opacity: 0.7" class="zhuye">管理页</span>
        </template>
          <el-menu-item index="1-1" style="font-size: 14px;padding-left: 52px;font-weight: 550;opacity: 0.7;color: black"><router-link to="/managerItem1">接口管理</router-link></el-menu-item>
          <el-menu-item index="1-2" style="font-size: 14px;padding-left: 52px;font-weight: 550;opacity: 0.7;color: black">接口分析</el-menu-item>
      </el-sub-menu>
    </el-menu>
  </el-col>
</div>
  <div class="right">
   <router-view/>
  </div>
</div>
</template>
<script setup>
</script>

<style scoped lang="less">
.box{
  background-color: #fcfcfc;
  display: flex;
  width: 100%;
  max-height: 800px;
  height: 760px;
  .left{
    width: 16%;
    height: 100%;
    border-right: darkgray solid 0.3px;
    .left-top{
      display: flex;
      height: 93px;
      align-content: center;
      flex-wrap: wrap;
      justify-content: center;
      border-bottom: darkgray solid 0.3px;
      img{
        margin-left: -31px;
        width: 60px;
        height: 60px;
      }
      h5{
        display: block;
        height: 60px;
        line-height: 60px;
        font-size: 17px;
        font-weight: 600;
        margin-left: 5px;
        opacity: 0.7;
      }
    }
    .img-item{
      width: 20px;
      height: 20px;
    }
    .zhuye:hover{
      //color: #e3effceb;
    }
  }
  .right{
    margin-left: 8px;
    width: 80%;
    height: 100%;
  }
}
</style>
