<script setup>
import { onMounted, ref } from 'vue'
import { getStudentDevelopList } from '@/api/develop.js'

const loading = ref(true)
const list = ref([])

// 完整模拟学生出国数据（补全设计稿所有院校）
const abroadStudents = ref([
  {
    id: 1,
    university: '普渡大学',
    students: '陶同学、郭同学、吴同学、杨同学',
    logo: 'https://via.placeholder.com/40/303133/fff?text=PU',
  },
  {
    id: 2,
    university: '卡内基梅隆大学',
    students: '谢同学、蔡同学、曾同学',
    logo: 'https://via.placeholder.com/40/303133/fff?text=CMU',
  },
  {
    id: 6,
    university: '佐治亚理工学院',
    students: '任同学',
    logo: 'https://via.placeholder.com/40/303133/fff?text=GT',
  },
  {
    id: 7,
    university: '马萨诸塞大学安姆斯特分校',
    students: '创阳同学',
    logo: 'https://via.placeholder.com/40/303133/fff?text=UMass',
  },
  {
    id: 11,
    university: '佛罗里达州立大学',
    students: '孟同学',
    logo: 'https://via.placeholder.com/40/303133/fff?text=FSU',
  },
  {
    id: 12,
    university: '墨尔本大学',
    students: '方同学',
    logo: 'https://via.placeholder.com/40/303133/fff?text=UMelb',
  },
  {
    id: 16,
    university: '明尼苏达大学',
    students: '黄同学',
    logo: 'https://via.placeholder.com/40/303133/fff?text=UMN',
  },
  {
    id: 17,
    university: '加利福尼亚大学圣迭戈分校',
    students: '谢同学',
    logo: 'https://via.placeholder.com/40/303133/fff?text=UCSD',
  },
])

// 完整模拟推荐交流数据
const recommendItems = ref([
  {
    id: 1,
    type: '合作院校',
    desc: '多名学生赴香港中文大学、新加坡南洋理工大学、加拿大阿尔伯塔大学、瑞典乌普萨拉大学等世界一流大学访问学习',
    logos: [
      'https://via.placeholder.com/80/409eff/fff?text=CUHK',
      'https://via.placeholder.com/80/409eff/fff?text=NTU',
      'https://via.placeholder.com/80/409eff/fff?text=UAlberta',
      'https://via.placeholder.com/80/409eff/fff?text=UU',
    ],
  },
  {
    id: 2,
    type: '论文录用',
    title: '实验室论文被 KNOSYS 期刊录用',
    time: '05月 09日',
    cover: 'https://via.placeholder.com/200x260/606266/fff?text=论文录用',
  },
  {
    id: 3,
    type: '平台上线',
    title: '区块链数据智能平台 XBLOCK中文版上线',
    time: '13月 08日',
    icon: '📊',
  },
])

// 完整模拟活动列表数据
const activityList = ref([
  {
    id: 1,
    file: 'https://via.placeholder.com/300x180/409eff/fff?text=交流活动1',
    title: '2025海外交流项目宣讲会',
    detail:
      '本次宣讲会将介绍2025年海外交流项目的申请条件、流程及奖学金政策，欢迎感兴趣的同学参加。',
    time: '2025-03-10',
  },
  {
    id: 2,
    file: 'https://via.placeholder.com/300x180/67c23a/fff?text=实习招聘',
    title: '2025届毕业生实习双选会',
    detail:
      '联合20余家知名企业举办实习双选会，涵盖大数据、人工智能、区块链等多个领域，提供超100个实习岗位。',
    time: '2025-04-15',
  },
  {
    id: 3,
    file: 'https://via.placeholder.com/300x180/e6a23c/fff?text=就业指导',
    title: '高端就业指导讲座',
    detail: '邀请行业资深专家分享求职技巧、简历制作、面试经验等内容，助力毕业生找到理想工作。',
    time: '2025-05-20',
  },
  {
    id: 4,
    file: 'https://via.placeholder.com/300x180/f56c6c/fff?text=荣誉表彰',
    title: '2024-2025学年优秀学生表彰大会',
    detail: '表彰在学术、实践、创新等方面表现突出的学生，颁发荣誉证书及奖学金。',
    time: '2025-01-10',
  },
  {
    id: 5,
    file: 'https://via.placeholder.com/300x180/909399/fff?text=海外研学',
    title: '暑期海外研学项目报名启动',
    detail: '暑期赴美国、新加坡、澳大利亚等国家的顶尖高校进行为期2周的研学活动，限30人报名。',
    time: '2025-02-28',
  },
  {
    id: 6,
    file: 'https://via.placeholder.com/300x180/722ed1/fff?text=校企合作',
    title: '校企合作实训基地揭牌仪式',
    detail: '与阿里云、百度智能云共建实训基地，为学生提供真实项目实践机会。',
    time: '2025-03-25',
  },
])

const GetDevelopList = async () => {
  // 模拟接口返回，直接使用本地模拟数据
  setTimeout(() => {
    list.value = activityList.value
    loading.value = false
  }, 500)
}

onMounted(() => {
  GetDevelopList()
})
</script>

<template>
  <div class="student-develop">
    <!-- 学生出国模块 -->
    <section class="section abroad-section">
      <h2 class="section-title">学生出国</h2>
      <div class="abroad-grid">
        <div v-for="item in abroadStudents" :key="item.id" class="abroad-card">
          <div class="abroad-number">{{ item.id }}</div>
          <div class="abroad-content">
            <div class="abroad-university">{{ item.university }}</div>
            <div class="abroad-students">{{ item.students }}</div>
          </div>
          <el-image :src="item.logo" class="abroad-logo" fit="contain" />
        </div>
      </div>
    </section>

    <!-- 推荐交流模块 -->
    <section class="section recommend-section">
      <h2 class="section-title">推荐交流</h2>
      <div class="recommend-grid">
        <!-- 合作院校卡片 -->
        <div class="recommend-card colleges-card" :key="recommendItems[0].id">
          <p class="recommend-desc">{{ recommendItems[0].desc }}</p>
          <div class="college-logos">
            <el-image
              v-for="(logo, idx) in recommendItems[0].logos"
              :key="idx"
              :src="logo"
              fit="contain"
              class="college-logo"
            />
          </div>
        </div>

        <!-- 其他推荐卡片 -->
        <div
          v-for="item in recommendItems.slice(1)"
          :key="item.id"
          class="recommend-card news-card"
        >
          <el-image v-if="item.cover" :src="item.cover" class="news-cover" fit="cover" />
          <div v-else class="news-icon">{{ item.icon }}</div>
          <div class="news-info">
            <div class="news-time">{{ item.time }}</div>
            <div class="news-label">最新新闻</div>
            <div class="news-title">{{ item.title }}</div>
          </div>
        </div>
      </div>
    </section>

    <!-- 活动列表（无分页，一页展示所有） -->
    <section class="section activity-section">
      <div v-loading="loading" class="wall-body">
        <div class="cards">
          <div v-for="e in list" :key="e.id" class="card">
            <el-image :src="e.file" fit="cover" class="pic" lazy />
            <div class="info">
              <div class="title">{{ e.title }}</div>
              <div class="desc">{{ e.detail }}</div>
              <el-tag size="small" class="tag">{{ e.time }}</el-tag>
            </div>
          </div>
        </div>

        <el-empty v-if="!loading && !list.length" description="暂无活动" />
      </div>
    </section>
  </div>
</template>

<style scoped lang="less">
.student-develop {
  max-width: 1200px;
  margin: 60px auto;
  padding: 20px 20px;
  background: #fafbfc;
  min-height: 100vh;
}

.page-title {
  text-align: center;
  margin-bottom: 50px;
  font-size: 36px;
  font-weight: 600;
  color: #303133;
}

.section {
  margin-bottom: 60px;

  .section-title {
    font-size: 24px;
    font-weight: 600;
    color: #303133;
    margin-bottom: 30px;
    text-align: center;
    position: relative;

    &::after {
      content: '';
      position: absolute;
      bottom: -10px;
      left: 50%;
      transform: translateX(-50%);
      width: 40px;
      height: 2px;
      background: #409eff;
    }
  }
}

// 学生出国模块样式
.abroad-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(260px, 1fr));
  gap: 25px;
}

.abroad-card {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 15px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);

  .abroad-number {
    width: 30px;
    height: 30px;
    background: #303133;
    color: #fff;
    border-radius: 4px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-weight: 600;
    flex-shrink: 0;
  }

  .abroad-content {
    flex: 1;

    .abroad-university {
      font-size: 16px;
      font-weight: 600;
      color: #303133;
      margin-bottom: 4px;
    }

    .abroad-students {
      font-size: 13px;
      color: #606266;
    }
  }

  .abroad-logo {
    width: 40px;
    height: 40px;
    flex-shrink: 0;
  }
}

// 推荐交流模块样式
.recommend-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 25px;
}

.recommend-card {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
}

.colleges-card {
  .recommend-desc {
    font-size: 15px;
    color: #606266;
    line-height: 1.6;
    margin-bottom: 20px;
    text-align: center;
  }

  .college-logos {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    gap: 20px;

    .college-logo {
      width: 80px;
      height: 80px;
    }
  }
}

.news-card {
  display: flex;
  gap: 15px;
  align-items: center;

  .news-cover {
    width: 120px;
    height: 160px;
    border-radius: 4px;
    flex-shrink: 0;
  }

  .news-icon {
    font-size: 40px;
    flex-shrink: 0;
    width: 120px;
    text-align: center;
  }

  .news-info {
    flex: 1;

    .news-time {
      font-size: 13px;
      color: #909399;
      margin-bottom: 4px;
    }

    .news-label {
      font-size: 12px;
      color: #409eff;
      margin-bottom: 8px;
    }

    .news-title {
      font-size: 16px;
      font-weight: 600;
      color: #303133;
      line-height: 1.5;
    }
  }
}

// 活动列表样式
.cards {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
}

.card {
  background: #fff;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  transition: transform 0.2s;

  &:hover {
    transform: translateY(-4px);
  }

  .pic {
    width: 100%;
    aspect-ratio: 16/9;
    object-fit: cover;
  }

  .info {
    padding: 14px 16px 16px;

    .title {
      font-size: 17px;
      font-weight: 600;
      color: #303133;
      margin-bottom: 6px;
    }

    .desc {
      font-size: 14px;
      color: #606266;
      line-height: 1.6;
      margin-bottom: 10px;
    }

    .tag {
      background: #f0f9ff;
      border-color: #d1e9ff;
      color: #409eff;
    }
  }
}

// 响应式适配
@media (max-width: 768px) {
  .abroad-grid,
  .recommend-grid,
  .cards {
    grid-template-columns: 1fr;
  }

  .news-card {
    flex-direction: column;
    text-align: center;
  }
}
</style>
