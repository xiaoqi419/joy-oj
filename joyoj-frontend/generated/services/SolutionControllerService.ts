/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseResponse_List_SolutionTagsVO_ } from '../models/BaseResponse_List_SolutionTagsVO_';
import type { SolutionTagsRequest } from '../models/SolutionTagsRequest';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class SolutionControllerService {
    /**
     * getRandomTags
     * @returns BaseResponse_List_SolutionTagsVO_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static getRandomTagsUsingPost(): CancelablePromise<BaseResponse_List_SolutionTagsVO_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/solution/randomTags',
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * getTags
     * @param solutionTagsRequest solutionTagsRequest
     * @returns BaseResponse_List_SolutionTagsVO_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static getTagsUsingPost(
        solutionTagsRequest: SolutionTagsRequest,
    ): CancelablePromise<BaseResponse_List_SolutionTagsVO_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/solution/tags',
            body: solutionTagsRequest,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
}
