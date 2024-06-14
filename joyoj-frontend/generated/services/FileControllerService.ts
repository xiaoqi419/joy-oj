/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseResponse } from '../models/BaseResponse';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class FileControllerService {
    /**
     * uploadFile
     * @param file file
     * @param biz
     * @returns BaseResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static uploadFileUsingPost(
        file: Blob,
        biz?: string,
    ): CancelablePromise<BaseResponse | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/file/upload',
            query: {
                'biz': biz,
            },
            formData: {
                'file': file,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
}
