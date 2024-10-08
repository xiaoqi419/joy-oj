# Joy Judge 🌟

欢迎来到 **Joy Judge** 🎉！这是一个基于 **Vue 3** 🌐、**Spring Boot** ☕ 和 **Docker** 🐳 的在线编程题目评测系统。

## 功能介绍 🛠️

### 前台功能 👩‍💻👨‍💻

- **题目管理** 📚：管理员可以创建、编辑和删除题目。
- **题目搜索** 🔍：用户可以自由搜索题目。
- **题目阅读** 📖：用户可以查看题目的详细信息。
- **代码提交** 💻：用户可以编写并提交代码进行评测。

### 后台功能 🖥️

- **测试用例执行** ✅：系统能够根据管理员设定的测试用例对用户提交的代码进行编译和运行。
- **结果判断** 🔍：系统判断输出是否正确，并反馈给用户。

### 代码沙箱 🏖️

- 提供一个独立服务用于编译和运行代码，便于其他开发者使用。

## 技术栈 🧑‍🔧

- **前端**：Vue 3
- **后端**：Spring Boot
- **容器化**：Docker

## 快速开始 🚀

1. 克隆项目：
   ```bash
   git clone https://github.com/xiaoqi419/joy-oj.git
   ```

2. 进入项目目录：
   ```bash
   cd joy-judge
   ```

3. 启动后端服务：
   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```

4. 启动前端服务：
   ```bash
   cd frontend
   npm install
   npm run serve
   ```

5. 访问系统：
    - 前端地址: `http://localhost:8080`
    - 后端地址: `http://localhost:8081`

## 贡献 🤝

欢迎任何形式的贡献！请查看 `CONTRIBUTING.md` 文件了解更多信息。

## 许可证 📝

此项目使用 MIT 许可证，详细信息请查看 `LICENSE` 文件。

感谢您使用 **Joy Judge**！我们希望您能享受这个在线评测系统的使用过程！😊
