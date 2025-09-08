<script setup>
import {  ref } from 'vue'
const list = ref([
  {
    name: '张三',
    project: '“中国科学技术奖”',
    time: '2025年9月7日',
    img: '../assets/images/1.jpg',
    id:1
  },
  {
    name: '李四',
    project: '“全国大学生数学竞赛”',
    time: '2025年8月15日',
    img: '../assets/images/2.jpg',
    id:2
  },
  {
    name: '王五',
    project: '“挑战杯”全国大学生课外学术科技作品竞赛',
    time: '2025年7月20日',
    img: '../assets/images/3.jpg',
    id:3
  },
  {
    name: '赵六',
    project: '“互联网+”大学生创新创业大赛',
    time: '2025年6月10日',
    img: '../assets/images/4.jpg',
    id:4
  }
])

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
              <p>获奖项目：{{item.project}}</p>
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