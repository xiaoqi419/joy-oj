/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {BaseResponse_boolean_} from '../models/BaseResponse_boolean_';
import type {BaseResponse_List_QuestionLanguage_} from '../models/BaseResponse_List_QuestionLanguage_';
import type {BaseResponse_long_} from '../models/BaseResponse_long_';
import type {BaseResponse_Page_Question_} from '../models/BaseResponse_Page_Question_';
import type {BaseResponse_Page_QuestionSubmitVO_} from '../models/BaseResponse_Page_QuestionSubmitVO_';
import type {BaseResponse_Page_QuestionVO_} from '../models/BaseResponse_Page_QuestionVO_';
import type {BaseResponse_Question_} from '../models/BaseResponse_Question_';
import type {BaseResponse_QuestionSaveVO_} from '../models/BaseResponse_QuestionSaveVO_';
import type {BaseResponse_QuestionVO_} from '../models/BaseResponse_QuestionVO_';
import type {BaseResponse_string_} from '../models/BaseResponse_string_';
import type {DeleteRequest} from '../models/DeleteRequest';
import type {QuestionAddRequest} from '../models/QuestionAddRequest';
import type {QuestionEditRequest} from '../models/QuestionEditRequest';
import type {QuestionQueryRequest} from '../models/QuestionQueryRequest';
import type {QuestionSaveAddRequest} from '../models/QuestionSaveAddRequest';
import type {QuestionSaveQueryRequest} from '../models/QuestionSaveQueryRequest';
import type {QuestionSubmitAddRequest} from '../models/QuestionSubmitAddRequest';
import type {QuestionSubmitQueryRequest} from '../models/QuestionSubmitQueryRequest';
import type {QuestionUpdateRequest} from '../models/QuestionUpdateRequest';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';

export class QuestionControllerService {
    /**
     * addQuestion
     * @param questionAddRequest questionAddRequest
     * @returns BaseResponse_long_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static addQuestionUsingPost(
        questionAddRequest: QuestionAddRequest,
    ): CancelablePromise<BaseResponse_long_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/question/add',
            body: questionAddRequest,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * deleteQuestion
     * @param deleteRequest deleteRequest
     * @returns BaseResponse_boolean_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static deleteQuestionUsingPost(
        deleteRequest: DeleteRequest,
    ): CancelablePromise<BaseResponse_boolean_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/question/delete',
            body: deleteRequest,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * editQuestion
     * @param questionEditRequest questionEditRequest
     * @returns BaseResponse_boolean_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static editQuestionUsingPost(
        questionEditRequest: QuestionEditRequest,
    ): CancelablePromise<BaseResponse_boolean_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/question/edit',
            body: questionEditRequest,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * getQuestionById
     * @param id id
     * @returns BaseResponse_Question_ OK
     * @throws ApiError
     */
    public static getQuestionByIdUsingGet(
        id?: number,
    ): CancelablePromise<BaseResponse_Question_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/question/get',
            query: {
                'id': id,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * getLanguages
     * @returns BaseResponse_List_QuestionLanguage_ OK
     * @throws ApiError
     */
    public static getLanguagesUsingGet(): CancelablePromise<BaseResponse_List_QuestionLanguage_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/question/get/languages',
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * getQuestionVOById
     * @param id id
     * @returns BaseResponse_QuestionVO_ OK
     * @throws ApiError
     */
    public static getQuestionVoByIdUsingGet(
        id?: number,
    ): CancelablePromise<BaseResponse_QuestionVO_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/question/get/vo',
            query: {
                'id': id,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * listQuestionByPage
     * @param questionQueryRequest questionQueryRequest
     * @returns BaseResponse_Page_Question_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static listQuestionByPageUsingPost(
        questionQueryRequest: QuestionQueryRequest,
    ): CancelablePromise<BaseResponse_Page_Question_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/question/list/page',
            body: questionQueryRequest,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * listQuestionVOByPage
     * @param questionQueryRequest questionQueryRequest
     * @returns BaseResponse_Page_QuestionVO_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static listQuestionVoByPageUsingPost(
        questionQueryRequest: QuestionQueryRequest,
    ): CancelablePromise<BaseResponse_Page_QuestionVO_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/question/list/page/vo',
            body: questionQueryRequest,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * listMyQuestionVOByPage
     * @param questionQueryRequest questionQueryRequest
     * @returns BaseResponse_Page_QuestionVO_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static listMyQuestionVoByPageUsingPost(
        questionQueryRequest: QuestionQueryRequest,
    ): CancelablePromise<BaseResponse_Page_QuestionVO_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/question/my/list/page/vo',
            body: questionQueryRequest,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * doQuestionSubmit
     * @param questionSubmitAddRequest questionSubmitAddRequest
     * @returns BaseResponse_long_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static doQuestionSubmitUsingPost(
        questionSubmitAddRequest: QuestionSubmitAddRequest,
    ): CancelablePromise<BaseResponse_long_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/question/question_submit/do',
            body: questionSubmitAddRequest,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * getQuestionSubmitById
     * @param questionSubmitQueryRequest questionSubmitQueryRequest
     * @returns BaseResponse_string_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static getQuestionSubmitByIdUsingPost(
        questionSubmitQueryRequest: QuestionSubmitQueryRequest,
    ): CancelablePromise<BaseResponse_string_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/question/question_submit/get',
            body: questionSubmitQueryRequest,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * listQuestionSubmitByPage
     * @param questionSubmitQueryRequest questionSubmitQueryRequest
     * @returns BaseResponse_Page_QuestionSubmitVO_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static listQuestionSubmitByPageUsingPost(
        questionSubmitQueryRequest: QuestionSubmitQueryRequest,
    ): CancelablePromise<BaseResponse_Page_QuestionSubmitVO_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/question/question_submit/list/page',
            body: questionSubmitQueryRequest,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * doLocalQuestionSubmit
     * @param questionSubmitAddRequest questionSubmitAddRequest
     * @returns BaseResponse_string_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static doLocalQuestionSubmitUsingPost(
        questionSubmitAddRequest: QuestionSubmitAddRequest,
    ): CancelablePromise<BaseResponse_string_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/question/question_submit/local/do',
            body: questionSubmitAddRequest,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * saveQuestionSubmit
     * @param questionSaveAddRequest questionSaveAddRequest
     * @returns BaseResponse_boolean_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static saveQuestionSubmitUsingPost(
        questionSaveAddRequest: QuestionSaveAddRequest,
    ): CancelablePromise<BaseResponse_boolean_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/question/question_submit/save',
            body: questionSaveAddRequest,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * getQuestionSave
     * @param questionSaveQueryRequest questionSaveQueryRequest
     * @returns BaseResponse_QuestionSaveVO_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static getQuestionSaveUsingPost(
        questionSaveQueryRequest: QuestionSaveQueryRequest,
    ): CancelablePromise<BaseResponse_QuestionSaveVO_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/question/question_submit/save/get',
            body: questionSaveQueryRequest,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
    /**
     * updateQuestion
     * @param questionUpdateRequest questionUpdateRequest
     * @returns BaseResponse_boolean_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static updateQuestionUsingPost(
        questionUpdateRequest: QuestionUpdateRequest,
    ): CancelablePromise<BaseResponse_boolean_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/question/update',
            body: questionUpdateRequest,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }
}
