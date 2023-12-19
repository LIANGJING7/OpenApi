<template>
<div class="bd">
  <div class="header">
    <div class="header-top">
      <img src="/API.png" alt="">
      <span>JING API</span>
    </div>
    <span class="header-bottom">一个丰富的API调用平台</span>
  </div>
  <div class="boxpro">
    <router-view/>
  </div>
</div>
</template>
<script setup>
</script>

<style scoped lang="less">
.bd{
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  align-items: center;
  justify-content: space-around;
}
.header{
  display: flex;
  flex-wrap: wrap;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 145px;
  .header-top{
    display: flex;
    justify-content: center;
    width: 280px;
    height: 60px;
    img{
      width: 50px;
      height: 50px;
    }
    span{
      margin-top: 10px;
      margin-left: 6px;
      font-weight: 600;
      font-size: 25px;
      opacity: 0.9;
    }
  }
  .header-bottom{
    font-size: 14px;
    font-weight: bolder;
    font-family: -apple-system,BlinkMacSystemFont,Segoe UI,Roboto,Helvetica Neue,Arial,Noto Sans,sans-serif,"Apple Color Emoji","Segoe UI Emoji",Segoe UI Symbol,"Noto Color Emoji";
    font-variant: tabular-nums;
    opacity: 0.7;
  }
}
</style>
