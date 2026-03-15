<template>
  <footer class="footer">
    <div class="footer-container">
      <div class="footer-content">
        <div class="footer-left">
          <div class="contact-info">
            <div class="info-item">
              <span class="info-icon">✉️</span>
              <span class="info-text">邮箱：cao@hut.cn</span>
            </div>
            <div class="info-item">
              <span class="info-icon">📍</span>
              <span class="info-text">地址：湖南工业大学计通楼</span>
            </div>
            <div class="info-item">
              <span class="info-icon">📞</span>
              <span class="info-text">电话：0571-85290220</span>
            </div>
          </div>
        </div>
        <div class="footer-right">
          <div class="footer-links">
            <a href="https://www.hut.edu.cn/" target="_blank" class="footer-link">
              <span>湖南工业大学</span>
              <span class="link-arrow">›</span>
            </a>
          </div>
          <div class="footer-links">
            <a href="https://computer.hut.edu.cn/" target="_blank" class="footer-link">
              <span>湖南工业大学计算机与人工智能学院</span>
              <span class="link-arrow">›</span>
            </a>
          </div>
        </div>
      </div>
    </div>
  </footer>
</template>

<style scoped>
.footer {
  background-color: #381640;
  color: #ffffff;
  padding: 30px 0;
  margin-top: 60px;
}

.footer-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

.footer-content {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.footer-left {
  flex: 1;
}

.contact-info {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.info-item {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 14px;
}

.info-icon {
  font-size: 16px;
  min-width: 20px;
}

.info-text {
  line-height: 1.4;
}

.footer-right {
  display: flex;
  gap: 80px;
}

.footer-links {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.footer-link {
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: #ffffff;
  text-decoration: none;
  font-size: 14px;
  padding-bottom: 5px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.3);
  min-width: 200px;
  transition: all 0.3s ease;
}

.footer-link:hover {
  border-bottom-color: #ffffff;
}

.link-arrow {
  margin-left: 10px;
  font-size: 16px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .footer-content {
    flex-direction: column;
    gap: 30px;
  }
  
  .footer-right {
    justify-content: space-around;
    width: 100%;
  }
}

@media (max-width: 480px) {
  .footer {
    padding: 20px 0;
  }
  
  .footer-right {
    flex-direction: column;
    gap: 20px;
  }
  
  .footer-link {
    min-width: auto;
  }
}
</style>