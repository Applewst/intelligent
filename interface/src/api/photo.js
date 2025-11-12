
import service from './request';
import { ref } from 'vue';
// 切换模式：true-使用模拟数据，false-使用真实AJAX请求
const USE_MOCK_DATA = true;
const List = ref([
  {
    id: 1,
    file: "https://example.com/team1.jpg",
    title: "团队第一次会议",
    time: "2023-10-01",
  },
  {
    id: 2,
    file: "https://example.com/team2.jpg",
    title: "团队外出活动",
    time: "2023-10-02",
  },
  {
    id: 3,
    file: "https://example.com/team3.jpg",
    title: "团队技术分享会",
    time: "2023-10-03",
  },
  {
    id: 4,
    file: "https://example.com/team3.jpg",
    title: "团队技术分享会",
    time: "2023-10-03",
  },
  {
    id: 5,
    file: "https://example.com/team3.jpg",
    title: "团队技术分享会",
    time: "2023-10-03",
  },
  {
    id: 6,
    file: "https://example.com/team3.jpg",
    title: "团队技术分享会",
    time: "2023-10-03",
  },
  {
    id: 7,
    file: "https://example.com/team3.jpg",
    title: "团队技术分享会",
    time: "2023-10-03",

  },
  {
    id: 8,
    file: "https://example.com/team3.jpg",
    title: "团队技术分享会",
    time: "2023-10-03",

  },
  {
    id: 9,
    file: "https://example.com/team3.jpg",
    title: "团队技术分享会",
    time: "2023-10-03"
  },
  {
    id: 10,
    file: "https://example.com/team3.jpg",
    title: "团队技术分享会",
    time: "2023-10-03"
  },
  {
    id: 11,
    file: "https://example.com/team3.jpg",
    title: "团队技术分享会",
    time: "2023-10-03"
  },
  {
    id: 12,
    file: "https://example.com/team3.jpg",
    title: "团队技术分享会",
    time: "2023-10-03"
  },
  {
    id: 13,
    file: "https://example.com/team3.jpg",
    title: "团队技术分享会",
    time: "2023-10-03"
  },
  {
    id: 14,
    file: "https://example.com/team3.jpg",
    title: "团队技术分享会",
    time: "2023-10-03"
  },

]);


export const getPhotoWallImages = async () => {
  // 使用模拟数据模式
  if (USE_MOCK_DATA) {
    console.log('获取照片墙API');
    return {
      "code":1,
      "message":"success",
      "data":List.value
    }
  }
  // return service.get('/team/shoots')
}
