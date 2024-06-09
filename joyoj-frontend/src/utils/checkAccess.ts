/**
 * 检查权限（判断当前用户是否有权限）
 */
import ACCESS_ENUM from '@/assets/enum/accessEnum'

const checkAccess = (loginUser: any, needAccess: any) => {
  // 获取当前登录用户的权限（如果没有loginUser，说明用户未登录，返回false）
  const loginUserAccess = loginUser?.access ?? ACCESS_ENUM.NOT_LOGIN
  if (needAccess.includes(loginUserAccess)) return true
  return false
}

export default checkAccess
