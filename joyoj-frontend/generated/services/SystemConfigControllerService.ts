/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseResponse } from '../models/BaseResponse';
import type { SystemBasic } from '../models/SystemBasic';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class SystemConfigControllerService {
    /**
     * changeBasicConfig
     * @param systemBasic systemBasic
     * @returns BaseResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static changeBasicConfigUsingPost(
        systemBasic: SystemBasic,
    ): CancelablePromise<BaseResponse | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/system_config/changeBasic',
            body: systemBasic,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * getBasicConfig
     * @returns BaseResponse OK
     * @throws ApiError
     */
    public static getBasicConfigUsingGet(): CancelablePromise<BaseResponse> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/system_config/getBasicConfig',
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
}
