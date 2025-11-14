<script setup>
import {  ref,onMounted } from 'vue'
import { GetAwards } from '@/api/search'
import { ElMessage } from 'element-plus'
const list = ref([])   //学生获奖列表

//获取学生获取情况
const GetAllAwardData = async (pageNum,pageSize,author) => {
  console.log('获取学生获奖数据文本处',pageNum,pageSize,author)
  const response = await GetAwards(pageNum,pageSize,author)
  if (response.code === 1) {
    list.value = response.data
    ElMessage.success('获取成功')
  }else{
    ElMessage.error('获取失败')
  }
}
onMounted(()=>{
  GetAllAwardData('','','')
})



</script>

<template>
  <div class="team-introduce">
    <div class="team-introduce-title">学生获奖</div>
    <el-row v-loading="loading" :gutter="20">
      <el-col v-for="item in list" :key="item.id" :xs="24" :sm="12" class="item-box">
        <el-card style="max-width: 520px; margin-bottom: 20px;" shadow="hover">
          <div class="item-content">
            <img src="../assets/images/1.jpg" alt="获奖图片" />
            <div class="item-infor">
              <h3>{{item.name}}</h3>
              <p>获奖人：{{item.author}}</p>
              <p>{{item.detail}}</p>
              <p class="make-time">{{item.time}}</p>
            </div>
            
          </div>
        </el-card>

      </el-col>
    
    </el-row>
 
</div>
</template>

<style scoped lang="less">
.team-introduce {
  text-align: center;
  margin-top: 100px;
  max-width: 1200px;
  margin: 100px auto;
  color: #333;
}
.team-introduce-title {
  font-weight: bold;
  font-size: 32px;
  margin-bottom: 20px;
}


.item-content{
  flex: 1;
  min-width: 0;
  display: flex;
  img{
    width: 100px;
    height: 100px;
    margin-right: 20px;
    object-fit: cover;
    border-radius: 6px;

  }
  .item-infor{
    text-align: left;
    h3{
      font-size: 20px;
      font-weight: bold;
      margin-bottom: 20px;
    }
    p{
      font-size: 16px;
      margin-bottom: 20px;
    }
    .make-time{
      color: #999;
    }
  }
}


</style>