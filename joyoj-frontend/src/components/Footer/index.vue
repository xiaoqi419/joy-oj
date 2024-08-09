<template>
  <footer class="footer">
    <div class="container">
      <div class="footer-section about">
        <h2>About Us</h2>
        <p>We provide the best services to our customers. Our mission is to empower individuals and organizations.</p>
      </div>
      <div class="footer-section links">
        <h2>Quick Links</h2>
        <a-space>
          <a-link href="/about">关于我们</a-link>
          <a-link href="/view/question">浏览题目</a-link>
          <a-link href="https://www.ojason.top">我的博客</a-link>
          <a-link href="https://ojason.top">个人主页</a-link>
        </a-space>
      </div>
      <div class="footer-section social">
        <h2>Follow Us</h2>
        <ul class="social-icons">
          <li><a href="#">
            <icon-twitter/>
          </a></li>
          <li><a href="#">
            <icon-github/>
          </a></li>
          <li><a href="#">
            <icon-weibo-circle-fill/>
          </a></li>
          <li><a href="#">
            <icon-qq-circle-fill/>
          </a></li>
        </ul>
      </div>
    </div>
    <div class="footer-bottom">
      <p>© 2024 Powered By
        <a-link href="https://github.com/xiaoqi419">Jason</a-link>
        | All Rights Reserved.
      </p>
    </div>
  </footer>
</template>

<script setup lang="ts">

</script>

<style scoped lang="scss">
.footer {
  background-color: #343a40;
  color: #ffffff;
  padding: 40px 0;
  margin-top: 120px;

  .container {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    max-width: 1140px;
    margin: 0 auto;
    padding: 0 15px;
  }

  .footer-section {
    flex: 1;
    margin-right: 20px;

    h2 {
      color: #f8f9fa;
      margin-bottom: 15px;
    }

    p {
      color: #adb5bd;
    }

    ul {
      list-style: none;
      padding: 0;

      li {
        margin-bottom: 10px;

        a {
          color: #adb5bd;
          text-decoration: none;

          &:hover {
            color: #ffffff;
          }
        }
      }
    }
  }

  .social-icons {
    display: flex;

    li {
      margin-right: 15px;

      a {
        font-size: 18px;
        color: #adb5bd;

        &:hover {
          color: #ffffff;
        }
      }
    }
  }

  .footer-bottom {
    text-align: center;
    margin-top: 20px;
    border-top: 1px solid #495057;
    padding-top: 20px;

    p {
      margin: 0;
      color: #adb5bd;
    }
  }
}
</style>
