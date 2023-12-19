<template>
  <div class="header">
    <h4>接口管理</h4>
  </div>
  <div class="center">
    <el-form :inline="true" :model="SelectInfo" class="demo-form-inline" style="height: 100%;width: 100%;    display: flex;
    justify-content: space-around;
    align-items: center;">
      <el-form-item label="接口名称" style="margin-right: 13px">
        <el-input v-model="SelectInfo.name" placeholder="接口名称"  style="width: 155px"/>
      </el-form-item>
      <el-form-item label="描述" style="margin-right: 13px">
        <el-input v-model="SelectInfo.description" placeholder="描述" clearable style="width: 155px"/>
      </el-form-item>
      <el-form-item label="请求方法" style="margin-right: 13px">
        <el-input v-model="SelectInfo.method" placeholder="请求方法" clearable style="width: 155px"/>
      </el-form-item>
      <el-form-item>
        <el-button @click="clear">重置</el-button>
        <el-button type="primary" @click="OnSelect">查询</el-button>
      </el-form-item>
    </el-form>
  </div>
  <div class="bottom">
    <div class="bottom-top">
      <h5>查询表格</h5>
<!--      新建表格-->
      <span>
        <a-button @click="showModal(1)">新建</a-button>
        <a-modal v-model:open="open" title="添加接口" @ok="insertinterfaceinfo" width="600px">
          <div class="modal-top-f">
                  <el-form
                    label-width="100px"
                    :model="newDate"
                    style="max-width: 460px"
                  >
                <el-form-item label="Name">
                <el-input v-model="newDate.name" placeholder="接口名称"/>
                </el-form-item>
               <el-form-item label="description">
               <el-input v-model="newDate.description" placeholder="描述" />
                </el-form-item>
                <el-form-item label="method">
                 <el-input v-model="newDate.method" placeholder="请求方法" />
                </el-form-item>
                    <el-form-item label="url">
                 <el-input v-model="newDate.url"  placeholder="接口地址"/>
                </el-form-item>
                    <el-form-item label="requestHeader">
                 <el-input v-model="newDate.requestHeader" placeholder="请求头"/>
                </el-form-item>
                    <el-form-item label="responseHeader">
                 <el-input v-model="newDate.responseHeader" placeholder="响应头"/>
                </el-form-item>
            </el-form>
          </div>
       </a-modal>
          </span>
    </div>
    <a-table bordered :data-source="dataSource" :columns="columns" >
      <template #bodyCell="{ column, record }" >
        <template v-if="column.dataIndex === 'operation'">
<!--          修改-->

         <div style="width: 120px;display: flex">
           <span>
        <a @click="showModal2(record)" style="margin-right: 5px">修改</a>
        <a-modal v-model:open="open2" title="修改接口" @ok="update(record)" width="600px">
            接口名称 : <a-input v-model:value="showDate.name" placeholder="Basic usage" style="margin:10px 0" />
            url :<a-input v-model:value="showDate.url" placeholder="Basic usage"  style="margin:10px 0"/>
            请求方法 :<a-input v-model:value="showDate.method" placeholder="Basic usage"  style="margin:10px 0"/>
            调用参数 :<a-input v-model:value="showDate.params" placeholder="Basic usage"  style="margin:10px 0" />
            请求头 :<a-input v-model:value="showDate.requestHeader" placeholder="Basic usage"  style="margin:10px 0" />
            响应头 :<a-input v-model:value="showDate.responseHeader" placeholder="Basic usage"  style="margin:10px 0"/>
            描述 :<a-input v-model:value="showDate.description" placeholder="Basic usage"  style="margin:10px 0"/>
        </a-modal>
          </span>
           <a @click="offLine(record)" style="margin-right:5px;display: inline-block;width: 35px" v-if="record.status === 1" >下线</a>
           <a @click="onLine(record)" style="margin-right:5px;display: inline-block;width: 35px" v-if="record.status === 0" >发布</a>
           <a-popconfirm
             v-if="dataSource.length"
             title="真的要删除吗?"
             @confirm="onDelete(record.id)"
           >
             <a style="margin-right: 5px">删除</a>
           </a-popconfirm>
         </div>
        </template>
      </template>
    </a-table>
  </div>
</template>
<script setup>
import { onMounted, reactive, ref } from 'vue'
import * as http from '@/request/http'
import router from '@/router'
import { message } from 'ant-design-vue'
import axios from 'axios'

const open = ref(false)
const open2 = ref(false)
const deleteData = reactive({
  id: 0
})
const showDate = reactive({
  id: 0,
  name: '无',
  status: 0,
  description: '无',
  url: '无',
  method: '无',
  params: '无',
  requestHeader: '无',
  responseHeader: '无'
})
// 添加接口信息参数
const newDate = reactive({
  name: '',
  description: '',
  method: '',
  url: '',
  requestHeader: '',
  responseHeader: ''
})
// 查询条件
const SelectInfo = reactive({
  name: '',
  description: '',
  method: '',
  sortField: 'id'
})
const columns = [
  {
    title: '接口名称',
    dataIndex: 'name',
    width: '30%'
  },
  {
    title: '描述',
    dataIndex: 'description'
  },
  {
    title: '请求方法',
    dataIndex: 'method'
  },
  {
    title: 'url',
    dataIndex: 'url'
  },
  {
    title: '请求参数',
    dataIndex: 'params'
  },
  {
    title: '请求头',
    dataIndex: 'requestHeader'
  },
  {
    title: '响应头',
    dataIndex: 'responseHeader'
  },
  {
    title: '状态(0下线1上线)',
    dataIndex: 'status'
  },
  {
    title: '操作',
    dataIndex: 'operation'
  }
]
// 页面列表全部信息
const dataSource = ref([
  {
    id: 0,
    name: '无',
    status: 0,
    description: '无',
    url: '无',
    method: '无',
    params: '无',
    requestHeader: '无',
    responseHeader: '无'
  }
])
const showModal = (val) => {
  if (val === 1) {
    open.value = true
  } if (val === 2) {
    console.log("'22222'")
    open2.value = true
  }
}
const showModal2 = (val) => {
  showDate.id = val.id
  showDate.name = val.name
  showDate.status = val.status
  showDate.description = val.description
  showDate.url = val.url
  showDate.method = val.method
  showDate.params = val.params
  showDate.requestHeader = val.requestHeader
  showDate.responseHeader = val.responseHeader
  open2.value = true
}
const status = ref('')
const update = (val) => {
  http.postSendJson('/interfaceInfo/update', showDate).then((res) => {
    if (res.data.code === 0) {
      router.push('/managerItem1')
    } else {
      info('修改失败')
    }
  }).catch(() => {
    info('修改失败')
  })
  open2.value = false
}
// 插入添加信息方法
const insertinterfaceinfo = () => {
  console.log("'我被出发啦'")
  http.postSendJson('/interfaceInfo/add', newDate).then((res) => {
    if (res.data.code === 0) {
      router.push('/managerItem1')
    } else {
      info('添加失败')
    }
  }).catch((e) => {
    info('添加失败')
  })
  open.value = false
}
// 页面渲染时列表方法
onMounted(() => {
  http.get('/interfaceInfo/list').then((res) => {
    dataSource.value = res.data.data
    if (res.data.data.status === 0) {
      status.value = '下线'
    }
  })
})
// 删除接口信息
const onDelete = key => {
  deleteData.id = key
  console.log(deleteData)
  http.postSendJson('/interfaceInfo/delete', deleteData).then((res) => {
    console.log(res)
    if (res.data.code === 0) {
      info('删除成功')
      window.location.reload()
    } else {
      info(res.data.message)
    }
  }).catch(() => {
    info('删除失败')
  })
}
// 条件查询
const OnSelect = () => {
  console.log(SelectInfo)
  http.get('/interfaceInfo/list/condition', SelectInfo).then((res) => {
    console.log(res)
    dataSource.value = res.data.data
  })
}

const offLine = (val) => {
  console.log(val)
  http.postSendJson('/interfaceInfo/offLine', val).then((res) => {
    if (res.data.code === 0) {
      info('下线成功')
      window.location.reload()
    } else {
      info('下线失败')
    }
  }).catch(() => {
    info('下线失败')
  })
}
const onLine = (val) => {
  console.log("'------------'")
  axios({
    url: 'http://localhost:8888/api/interfaceInfo/onLine',
    method: 'post',
    data: JSON.stringify(val),
    headers: {
      'Content-Type': 'application/json;charset=utf-8',
      accessKey: window.sessionStorage.getItem('accessKey'),
      secretKey: window.sessionStorage.getItem('secretKey')
    }
  }).then((res) => {
    console.log(res)
    if (res.data.code === 0) {
      info('上线成功')
      window.location.reload()
    } else {
      info('上线失败,接口调用异常')
    }
  }).catch(() => {
    info('上线失败,接口调用异常')
  })
}
//   http.postSendJson('/interfaceInfo/onLine', val).then((res) => {
//     console.log(res)
//     if (res.data.code === 0) {
//       info('上线成功')
//       router.push('/managerItem1')
//     } else {
//       info('上线失败,接口调用异常')
//     }
//   }).catch(() => {
//     info('上线失败,接口调用异常')
//   })
// }
// 重置输入框
const clear = () => {
  SelectInfo.name = ''
  SelectInfo.description = ''
  SelectInfo.method = ''
}

const info = (msg) => {
  message.info(msg)
}
</script>
<style scoped lang="less">

.header {
  display: flex;
  height: 89px;
  justify-content: left;
  align-items: center;
  font-size: 24px;

  h4 {
    font-weight: 550;
    opacity: 0.7;
  }
}

.bottom {

  width: 96%;
  margin: auto;
  background-color: white;
}

.bottom-top {
  height: 50px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-left: 10px;
  padding-right: 10px;

  h5 {
    font-size: 16px;
    font-weight: 550;
    opacity: 0.8;
  }
}

.demo-form-inline .el-input {
  --el-input-width: 220px;
}

.center {
  width: 100%;
  height: 60px;
}
</style>
