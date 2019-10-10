const axios = require("axios");

export class API {
  constructor(controllerName) {
    this._apiUrl = this.getApiPath() + controllerName;
  }

  getApiPath() {
    var apiPath = null;
    const servers = {
      localhost: "http://localhost:1992/",
      prod: ""
    };

    if (window.location.host.includes("localhost")) {
      apiPath = servers["localhost"];
    }

    return apiPath;
  }

  get apiUrl() {
    return this._apiUrl;
  }

  set apiUrl(apiUrl) {
    this._apiUrl = apiUrl;
  }

  get(requestBody = null) {
    return axios
      .get(this.apiUrl, requestBody)
      .then(result => {
        return Promise.resolve(result.data);
      })
      .catch(error => {
        console.error(error);
        return Promise.reject(error);
      });
  }

  post(config, requestData = null) {
    return axios
      .post(this.apiUrl, requestData, config)
      .then(result => {
        return Promise.resolve(result);
      })
      .catch(error => {
        console.error(error);
        return Promise.reject(error);
      });
  }

  put(config, requestBody) {
    return axios
      .put(this.apiUrl, requestBody, config)
      .then(result => {
        return Promise.resolve(result);
      })
      .catch(error => {
        console.error(error);
        return Promise.reject(error);
      });
  }

  delete(requestBody) {
    return axios
      .delete(this.apiUrl, requestBody)
      .then(result => {
        return Promise.resolve(result);
      })
      .catch(error => {
        console.error(error);
        return Promise.reject(error);
      });
  }
}
