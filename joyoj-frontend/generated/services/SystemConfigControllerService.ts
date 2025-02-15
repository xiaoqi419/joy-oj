/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {BaseResponse_boolean_} from '../models/BaseResponse_boolean_';
import type {BaseResponse_SystemBasic_} from '../models/BaseResponse_SystemBasic_';
import type {SystemBasic} from '../models/SystemBasic';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';

export class SystemConfigControllerService {
    /**
     * changeBasicConfig
     * @param systemBasic systemBasic
     * @returns BaseResponse_boolean_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static changeBasicConfigUsingPost(
        systemBasic: SystemBasic,
    ): CancelablePromise<BaseResponse_boolean_ | any> {
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
     * @returns BaseResponse_SystemBasic_ OK
     * @throws ApiError
     */
    public static getBasicConfigUsingGet(): CancelablePromise<BaseResponse_SystemBasic_> {
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
