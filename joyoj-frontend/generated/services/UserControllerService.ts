/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseResponse } from '../models/BaseResponse'
import type { DeleteRequest } from '../models/DeleteRequest'
import type { UserAddRequest } from '../models/UserAddRequest'
import type { UserLoginRequest } from '../models/UserLoginRequest'
import type { UserQueryRequest } from '../models/UserQueryRequest'
import type { UserRegisterRequest } from '../models/UserRegisterRequest'
import type { UserUpdateMyRequest } from '../models/UserUpdateMyRequest'
import type { UserUpdateRequest } from '../models/UserUpdateRequest'
import type { CancelablePromise } from '../core/CancelablePromise'
import { OpenAPI } from '../core/OpenAPI'
import { request as __request } from '../core/request'

export class UserControllerService {
  /**
   * addUser
   * @param userAddRequest userAddRequest
   * @returns BaseResponse OK
   * @returns any Created
   * @throws ApiError
   */
  public static addUserUsingPost (
    userAddRequest: UserAddRequest,
  ): CancelablePromise<BaseResponse | any> {
    return __request(OpenAPI, {
      method: 'POST',
      url: '/api/user/add',
      body: userAddRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    })
  }

  /**
   * deleteUser
   * @param deleteRequest deleteRequest
   * @returns BaseResponse OK
   * @returns any Created
   * @throws ApiError
   */
  public static deleteUserUsingPost (
    deleteRequest: DeleteRequest,
  ): CancelablePromise<BaseResponse | any> {
    return __request(OpenAPI, {
      method: 'POST',
      url: '/api/user/delete',
      body: deleteRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    })
  }

  /**
   * getUserById
   * @param id id
   * @returns BaseResponse OK
   * @throws ApiError
   */
  public static getUserByIdUsingGet (
    id?: number,
  ): CancelablePromise<BaseResponse> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/user/get',
      query: {
        'id': id,
      },
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    })
  }

  /**
   * getLoginUser
   * @returns BaseResponse OK
   * @throws ApiError
   */
  public static getLoginUserUsingGet (): CancelablePromise<BaseResponse> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/user/get/login',
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    })
  }

  /**
   * getUserVOById
   * @param id id
   * @returns BaseResponse OK
   * @throws ApiError
   */
  public static getUserVoByIdUsingGet (
    id?: number,
  ): CancelablePromise<BaseResponse> {
    return __request(OpenAPI, {
      method: 'GET',
      url: '/api/user/get/vo',
      query: {
        'id': id,
      },
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    })
  }

  /**
   * listUserByPage
   * @param userQueryRequest userQueryRequest
   * @returns BaseResponse OK
   * @returns any Created
   * @throws ApiError
   */
  public static listUserByPageUsingPost (
    userQueryRequest: UserQueryRequest,
  ): CancelablePromise<BaseResponse | any> {
    return __request(OpenAPI, {
      method: 'POST',
      url: '/api/user/list/page',
      body: userQueryRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    })
  }

  /**
   * listUserVOByPage
   * @param userQueryRequest userQueryRequest
   * @returns BaseResponse OK
   * @returns any Created
   * @throws ApiError
   */
  public static listUserVoByPageUsingPost (
    userQueryRequest: UserQueryRequest,
  ): CancelablePromise<BaseResponse | any> {
    return __request(OpenAPI, {
      method: 'POST',
      url: '/api/user/list/page/vo',
      body: userQueryRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    })
  }

  /**
   * userLogin
   * @param userLoginRequest userLoginRequest
   * @returns BaseResponse OK
   * @returns any Created
   * @throws ApiError
   */
  public static userLoginUsingPost (
    userLoginRequest: UserLoginRequest,
  ): CancelablePromise<BaseResponse | any> {
    return __request(OpenAPI, {
      method: 'POST',
      url: '/api/user/login',
      body: userLoginRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    })
  }

  /**
   * userLogout
   * @returns BaseResponse OK
   * @returns any Created
   * @throws ApiError
   */
  public static userLogoutUsingPost (): CancelablePromise<BaseResponse | any> {
    return __request(OpenAPI, {
      method: 'POST',
      url: '/api/user/logout',
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    })
  }

  /**
   * userRegister
   * @param userRegisterRequest userRegisterRequest
   * @returns BaseResponse OK
   * @returns any Created
   * @throws ApiError
   */
  public static userRegisterUsingPost (
    userRegisterRequest: UserRegisterRequest,
  ): CancelablePromise<BaseResponse | any> {
    return __request(OpenAPI, {
      method: 'POST',
      url: '/api/user/register',
      body: userRegisterRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    })
  }

  /**
   * updateUser
   * @param userUpdateRequest userUpdateRequest
   * @returns BaseResponse OK
   * @returns any Created
   * @throws ApiError
   */
  public static updateUserUsingPost (
    userUpdateRequest: UserUpdateRequest,
  ): CancelablePromise<BaseResponse | any> {
    return __request(OpenAPI, {
      method: 'POST',
      url: '/api/user/update',
      body: userUpdateRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    })
  }

  /**
   * updateMyUser
   * @param userUpdateMyRequest userUpdateMyRequest
   * @returns BaseResponse OK
   * @returns any Created
   * @throws ApiError
   */
  public static updateMyUserUsingPost (
    userUpdateMyRequest: UserUpdateMyRequest,
  ): CancelablePromise<BaseResponse | any> {
    return __request(OpenAPI, {
      method: 'POST',
      url: '/api/user/update/my',
      body: userUpdateMyRequest,
      errors: {
        401: `Unauthorized`,
        403: `Forbidden`,
        404: `Not Found`,
      },
    })
  }
}
