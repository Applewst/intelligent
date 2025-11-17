import request from "./request"

// 模拟数据
let mockData = [
  {
    id: 1,
    image: "/artificial-intelligence-research.jpg",
    title: "人工智能在医疗诊断中的应用研究",
    detail:
      "本研究探讨了深度学习算法在医学影像分析中的应用，通过卷积神经网络模型实现了对肺部CT图像的自动化诊断，准确率达到95%以上。",
    time: "2024-03-15 10:30:00",
  },
  {
    id: 2,
    image: "/quantum-computing-concept.png",
    title: "量子计算在密码学领域的突破",
    detail: "团队成功开发了新型量子加密算法，能够抵御传统和量子计算机的双重攻击，为未来网络安全提供了新的解决方案。",
    time: "2024-03-14 14:20:00",
  },
  {
    id: 3,
    image: "/renewable-energy.jpg",
    title: "新型太阳能电池效率突破30%",
    detail:
      "研究团队利用钙钛矿材料，成功将太阳能电池的转换效率提升至30.5%，创造了新的世界纪录，为清洁能源发展带来新希望。",
    time: "2024-03-13 09:15:00",
  },
  {
    id: 4,
    image: "/nanotechnology.jpg",
    title: "纳米机器人在癌症治疗中的应用",
    detail: "开发出可精准定位癌细胞的纳米机器人系统，能够在不损伤正常细胞的情况下，靶向释放抗癌药物，治疗效果显著。",
    time: "2024-03-12 16:45:00",
  },
  {
    id: 5,
    image: "/space-exploration.jpg",
    title: "深空探测器成功返回火星样本",
    detail: "历时3年的火星探测任务圆满成功，带回了珍贵的火星土壤和岩石样本，为研究火星地质历史提供了第一手资料。",
    time: "2024-03-11 11:00:00",
  },
  {
    id: 6,
    image: "/biotechnology-research.png",
    title: "基因编辑技术在农业中的创新应用",
    detail: "利用CRISPR技术成功培育出抗旱、高产的新型水稻品种，产量提升40%，有望解决粮食安全问题。",
    time: "2024-03-10 13:30:00",
  },
  {
    id: 7,
    image: "/neuroscience.jpg",
    title: "脑机接口技术助力残障人士重获行动能力",
    detail: "新一代脑机接口系统能够解读大脑信号，帮助瘫痪患者通过意念控制机械臂和轮椅，极大改善了生活质量。",
    time: "2024-03-09 15:20:00",
  },
  {
    id: 8,
    image: "/climate-science.jpg",
    title: "全球气候模型预测精度提升",
    detail: "结合人工智能和大数据技术，开发出新型气候预测模型，对极端天气事件的预测准确度提高了25%。",
    time: "2024-03-08 10:10:00",
  },
  {
    id: 9,
    image: "/materials-science.jpg",
    title: "超导材料在室温下实现零电阻",
    detail: "科研团队发现了新型室温超导材料，这一突破将彻底改变电力传输和储存技术，推动能源革命。",
    time: "2024-03-07 14:50:00",
  },
  {
    id: 10,
    image: "/futuristic-robotics-lab.png",
    title: "仿生机器人实现复杂地形自主导航",
    detail: "受动物运动启发开发的四足机器人，能够在崎岖地形中自主行走，应用于搜救和探险任务。",
    time: "2024-03-06 09:40:00",
  },
  {
    id: 11,
    image: "/marine-biology.jpg",
    title: "深海生物多样性调查取得重大发现",
    detail: "在马里亚纳海沟发现200多个新物种，揭示了深海生态系统的复杂性和生命适应极端环境的能力。",
    time: "2024-03-05 16:25:00",
  },
  {
    id: 12,
    image: "/5g-technology.jpg",
    title: "6G通信技术研发取得阶段性成果",
    detail: "实验室环境下实现了太赫兹频段的稳定通信，传输速率达到1Tbps，为6G商用奠定了技术基础。",
    time: "2024-03-04 12:00:00",
  },
]

let nextId = 13

// 模拟函数
export const mockGetList = (pageNum, pageSize, name = "") => {
  return new Promise((resolve) => {
    setTimeout(() => {
      let filteredData = mockData
      if (name) {
        filteredData = mockData.filter((item) => item.title.includes(name))
      }

      const start = (pageNum - 1) * pageSize
      const end = start + pageSize
      const records = filteredData.slice(start, end)

      resolve({
        code: 200,
        data: {
          records,
          total: filteredData.length,
          pageNum,
          pageSize,
        },
      })
    }, 300)
  })
}

export const mockAdd = (data) => {
  return new Promise((resolve) => {
    setTimeout(() => {
      const newItem = {
        ...data,
        id: nextId++,
      }
      mockData.unshift(newItem)
      resolve({ code: 200, message: "添加成功" })
    }, 300)
  })
}

export const mockUpdate = (data) => {
  return new Promise((resolve) => {
    setTimeout(() => {
      const index = mockData.findIndex((item) => item.id === data.id)
      if (index !== -1) {
        mockData[index] = { ...data }
      }
      resolve({ code: 200, message: "更新成功" })
    }, 300)
  })
}

export const mockDelete = (id) => {
  return new Promise((resolve) => {
    setTimeout(() => {
      mockData = mockData.filter((item) => item.id !== id)
      resolve({ code: 200, message: "删除成功" })
    }, 300)
  })
}

// 真实API接口
export const getResearchList = (pageNum, pageSize, title = "") => {
  console.log('获取科研API',pageNum,pageSize,title)
  
  return request.get("/new/list", {
    params: { pageNum, pageSize, title },
  })
}

export const addResearch = (data) => {
  return request.post("/news", data)
}

export const updateResearch = (data) => {
  return request.put("/news/Pall", data)
}

export const deleteResearch = (id) => {
  return request.delete("/news/all", {
    params: { id } 
  })
}
