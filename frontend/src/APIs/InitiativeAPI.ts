import axios from "axios";
import {AuthError, TokenData, ValidateCode} from "./OauthAPI";
import {Base64} from "js-base64";


export enum InitiativeType {
    SOCIAL = 'SOCIAL',
    FOOD= 'FOOD',
    HEALTH = 'HEALTH',
    SPORT = 'SPORT',
    EDUCATIONAL = 'EDUCATIONAL',
    ENVIRONMENTAL = 'ENVIRONMENTAL',
    OTHER = 'OTHER'
}

export enum InitiativeTypeColor {
    SOCIAL = '#FFC107',
    FOOD= '#FF5722',
    HEALTH = '#4CAF50',
    SPORT = '#2196F3',
    EDUCATIONAL = '#9C27B0',
    ENVIRONMENTAL = '#795548',
    OTHER = '#9E9E9E'

}

export interface Initiative {
    id: string,
    name: string,
    description: string;
    type: InitiativeType;
    idCreator: string;
    idOrganizers: string[];
    idMembers: string[];
    startDate: number;
    endDate: number;
    location: string;
}

export const getAllInitiatives = async (tokenData: TokenData): Promise<Initiative[] | AuthError> => {
    const emailBase64 = Base64.encode(tokenData.email)
    const providerBase64 = Base64.encode(tokenData.provider)
    const tokenBase64 = Base64.encode(tokenData.token)
    const url = 'http://localhost:8080/initiativeAPI/v1/initiatives/' + tokenData.email + '?email=' + emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64
    return await axios.get(url).then((response) => {
        console.log('response', response.data)
        return response.data
    }).catch(() => {
        const authError: AuthError = {
            code: ValidateCode.GET_FAIL,
            method: 'GET',
            requestedPath: url
        }
        return authError
    })
}

export const getMyInitiatives = async (tokenData: TokenData): Promise<Initiative[] | AuthError> => {
    const emailBase64 = Base64.encode(tokenData.email)
    const providerBase64 = Base64.encode(tokenData.provider)
    const tokenBase64 = Base64.encode(tokenData.token)
    const url = 'http://localhost:8080/initiativeAPI/v1/initiative/getCreatedInitiatives/' + tokenData.email + '?email=' + emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64
    return await axios.get(url).then((response) => {
        console.log('response', response.data)
        return response.data
    }).catch(() => {
        const authError: AuthError = {
            code: ValidateCode.GET_FAIL,
            method: 'GET',
            requestedPath: url
        }
        return authError
    })
}

export const getSubscribedInitiatives = async (tokenData: TokenData): Promise<Initiative[] | AuthError> => {
    const emailBase64 = Base64.encode(tokenData.email)
    const providerBase64 = Base64.encode(tokenData.provider)
    const tokenBase64 = Base64.encode(tokenData.token)
    const url = 'http://localhost:8080/initiativeAPI/v1/initiative/getMySubscribedInitiatives/' + tokenData.email + '?email=' + emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64
    return await axios.get(url).then((response) => {
        console.log('response', response.data)
        return response.data
    }).catch(() => {
        const authError: AuthError = {
            code: ValidateCode.GET_FAIL,
            method: 'GET',
            requestedPath: url
        }
        return authError
    })
}

export const getOrganizedInitiatives = async (tokenData: TokenData): Promise<Initiative[] | AuthError> => {
    const emailBase64 = Base64.encode(tokenData.email)
    const providerBase64 = Base64.encode(tokenData.provider)
    const tokenBase64 = Base64.encode(tokenData.token)
    const url = 'http://localhost:8080/initiativeAPI/v1/initiative/getOrganizedInitiatives/' + tokenData.email + '?email=' + emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64
    return await axios.get(url).then((response) => {
        console.log('response', response.data)
        return response.data
    }).catch(() => {
        const authError: AuthError = {
            code: ValidateCode.GET_FAIL,
            method: 'GET',
            requestedPath: url
        }
        return authError
    })
}


export const createInitiative = async (tokenData: TokenData, initiative: Initiative) => {
    const emailBase64 = Base64.encode(tokenData.email)
    const providerBase64 = Base64.encode(tokenData.provider)
    const tokenBase64 = Base64.encode(tokenData.token)

    const url = 'http://localhost:8080/initiativeAPI/v1/initiative/create/' + tokenData.email + '?email=' + emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64

    return await axios.post<boolean | AuthError>(url,
        JSON.stringify(initiative),
        {
            headers: {"Content-Type": "text/plain"}
        })
        .then(value => {
            return value.data;
        }).catch(function (error) {
            console.log(error)
            const authError: AuthError = {
                code: ValidateCode.UPDATE_FAIL,
                method: 'POST',
                requestedPath: url.split('?')[0]
            }
            return authError
        });
}

export interface InitiativeNameDesc {
    name: string,
    description: string
    location: string
    type: InitiativeType
}

export const getInitiativesForVisitor = async (): Promise<InitiativeNameDesc[] | AuthError> => {
    const url = 'http://localhost:8080/initiativeAPI/v1/getAllNamesDesc'
    return await axios.get(url).then((response) => {
        console.log('response', response.data)
        return response.data
    }).catch(() => {
        const authError: AuthError = {
            code: ValidateCode.GET_FAIL,
            method: 'GET',
            requestedPath: url
        }
        return authError
    })
}

export const modifyInitiative = async (tokenData: TokenData, initiative: Initiative): Promise<boolean|AuthError> => {
    const emailBase64 = Base64.encode(tokenData.email)
    const providerBase64 = Base64.encode(tokenData.provider)
    const tokenBase64 = Base64.encode(tokenData.token)

    const url = 'http://localhost:8080/initiativeAPI/v1/initiative/modify/'+initiative.id+'/'
        + tokenData.email + '?email=' + emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64

    return await axios.post<boolean | AuthError>(url,
        JSON.stringify(initiative),
        {
            headers: {"Content-Type": "text/plain"}
        })
        .then(value => {
            return value.data;
        }).catch(function (error) {
            console.log(error)
            const authError: AuthError = {
                code: ValidateCode.UPDATE_FAIL,
                method: 'POST',
                requestedPath: url.split('?')[0]
            }
            return authError
        });
}

export const deleteInitiative= async (tokenData: TokenData, initiativeId: string): Promise<boolean|AuthError> => {
    const emailBase64 = Base64.encode(tokenData.email)
    const providerBase64 = Base64.encode(tokenData.provider)
    const tokenBase64 = Base64.encode(tokenData.token)

    const url = 'http://localhost:8080/initiativeAPI/v1/initiative/remove/'+initiativeId+'/'
        + tokenData.email + '?email=' + emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64

    return await axios.get<boolean | AuthError>(url)
        .then(value => {
            return value.data;
        }).catch(function (error) {
            console.log(error)
            const authError: AuthError = {
                code: ValidateCode.DELETE_FAIL,
                method: 'DELETE',
                requestedPath: url.split('?')[0]
            }
            return authError
        });
}

export const subscribeInitiative = async (tokenData: TokenData, initiativeId: string): Promise<boolean|AuthError> => {
    const emailBase64 = Base64.encode(tokenData.email)
    const providerBase64 = Base64.encode(tokenData.provider)
    const tokenBase64 = Base64.encode(tokenData.token)

    const url = 'http://localhost:8080/initiativeAPI/v1/initiative/subscribe/'+initiativeId+'/'
        + tokenData.email + '?email=' + emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64

    return await axios.get<boolean | AuthError>(url)
        .then(value => {
            return value.data;
        }).catch(function (error) {
            console.log(error)
            const authError: AuthError = {
                code: ValidateCode.UPDATE_FAIL,
                method: 'POST',
                requestedPath: url.split('?')[0]
            }
            return authError
        });
}

export const unsubscribeInitiative = async (tokenData: TokenData, initiativeId: string): Promise<boolean|AuthError> => {
    const emailBase64 = Base64.encode(tokenData.email)
    const providerBase64 = Base64.encode(tokenData.provider)
    const tokenBase64 = Base64.encode(tokenData.token)

    const url = 'http://localhost:8080/initiativeAPI/v1/initiative/unsubscribe/'+initiativeId+'/'
        + tokenData.email + '?email=' + emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64

    return await axios.get<boolean | AuthError>(url)
        .then(value => {
            return value.data;
        }).catch(function (error) {
            console.log(error)
            const authError: AuthError = {
                code: ValidateCode.UPDATE_FAIL,
                method: 'POST',
                requestedPath: url.split('?')[0]
            }
            return authError
        });
}

export const getInitiativeByID = async (tokenData: TokenData,initiativeID: string): Promise<Initiative | AuthError> => {
    const emailBase64 = Base64.encode(tokenData.email)
    const providerBase64 = Base64.encode(tokenData.provider)
    const tokenBase64 = Base64.encode(tokenData.token)

    const url = 'http://localhost:8080/initiativeAPI/v1/initiative/getInitiative/'+initiativeID+'/'
        + tokenData.email + '?email=' + emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64

    return await axios.get<Initiative | AuthError>(url)
        .then(value => {
            return value.data;
        }).catch(function (error) {
            console.log(error)
            const authError: AuthError = {
                code: ValidateCode.GET_FAIL,
                method: 'GET',
                requestedPath: url.split('?')[0]
            }
            return authError
        });
}

export const changeOrganizers = async (tokenData: TokenData, initiativeId: string, organizers: string[]): Promise<boolean|AuthError> => {
    const emailBase64 = Base64.encode(tokenData.email)
    const providerBase64 = Base64.encode(tokenData.provider)
    const tokenBase64 = Base64.encode(tokenData.token)

    const url = 'http://localhost:8080/initiativeAPI/v1/initiative/changeOrganizers/'+initiativeId+'/'
        + tokenData.email + '?email=' + emailBase64 + '&provider=' + providerBase64 + '&token=' + tokenBase64

    return await axios.post<boolean | AuthError>(url,
        JSON.stringify(organizers),
        {
            headers: {"Content-Type": "text/plain"}
        })
        .then(value => {
            return value.data;
        }).catch(function (error) {
            console.log(error)
            const authError: AuthError = {
                code: ValidateCode.UPDATE_FAIL,
                method: 'POST',
                requestedPath: url.split('?')[0]
            }
            return authError
        });
}