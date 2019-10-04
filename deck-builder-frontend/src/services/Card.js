import { API } from "@/models/API.js";

export default {
  getAllCards: function() {
    const getAllCardsAPI = new API("api/cards");
    return getAllCardsAPI.get();
  },

  getCardImage: function(card) {
    const getCardImageAPI = new API(`images/cards/${card.idName}.png`);
    return getCardImageAPI.get();
  }
};
