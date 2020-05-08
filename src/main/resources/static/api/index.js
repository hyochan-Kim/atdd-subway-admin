const BASE_URL = "http://localhost:8080";

const METHOD = {
    PUT(data) {
        return {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        };
    },
    DELETE() {
        return {
            method: "DELETE"
        };
    },
    POST(data) {
        return {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        };
    }
};

export const api = (() => {
    const request = (uri, config) => fetch(uri, config).then(data => data.json());
    const requestWithoutResponseBody = (uri, config) => fetch(uri, config);

    const station = {
        get() {
            return request(`${BASE_URL}/station`);
        },
        create(data) {
            console.log(data)
            return request(`${BASE_URL}/station`, METHOD.POST(data));
        },
        update(data) {
            return request(`${BASE_URL}/station/${id}`, METHOD.PUT(data));
        },
        delete(name) {
            return requestWithoutResponseBody(
                `${BASE_URL}/station/${name}`, METHOD.DELETE());
        }
    };


    const line = {
        get() {
            return request(`${BASE_URL}/line`);
        },
        create(data) {
            return request(`${BASE_URL}/line`, METHOD.POST(data));
        },
        update(lineId, data) {
            return requestWithoutResponseBody(`${BASE_URL}/line/${lineId}`, METHOD.PUT(data));
        },
        delete(lineId) {
            console.log(lineId)
            return requestWithoutResponseBody(
                `${BASE_URL}/line/${lineId}`, METHOD.DELETE());
        }
    };
    return {
        station,
        line
    };
})();
