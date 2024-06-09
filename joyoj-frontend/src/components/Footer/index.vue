<template>
  <footer class="footer">
    <div class="container">
      <p class="copyright">© 2024 Powered By Jason</p>
      <ul class="links">
        <li><a href="/about">About</a></li>
        <li><a href="/terms">Terms</a></li>
        <li><a href="/privacy">Privacy</a></li>
      </ul>
    </div>
  </footer>
</template>

<script setup lang="ts">
// You can add your script here
</script>

<style scoped lang="scss">
.footer {
  background-color: #f8f9fa;
  padding: 20px 0;
  border-top: 1px solid #e9ecef;

  .container {
    display: flex;
    justify-content: space-between;
    align-items: center;
    max-width: 1140px;
    margin: 0 auto;
    padding: 0 15px;
  }

  .copyright {
    color: #6c757d;
    margin: 0;
  }

  .links {
    list-style: none;
    padding: 0;
    margin: 0;
    display: flex;

    li {
      margin-left: 15px;

      a {
        color: #6c757d;
        text-decoration: none;

        &:hover {
          color: #343a40;
        }
      }
    }
  }
}
</style>
