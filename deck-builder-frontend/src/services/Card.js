import { API } from "@/models/API.js";

export default {
  getAllCards: function() {
    const getAllCardsAPI = new API("api/cards");
    return getAllCardsAPI.get();
  }
};
