<template>
  <div class="card-wrapper">
    <div v-for="card of cardsCollection"
         v-bind:key="card.id"
         class="card">
      <deck-card :card="card" />
    </div>
  </div>
</template>

<script>
import { Card } from "@/models/Card.js";
import CardService from "@/services/Card.js";
import DeckCard from "@/components/Card.vue";

export default {
  name: "app",

  components: {
    DeckCard
  },

  data() {
    return {
      cardsCollection: [],
      cardsRarities: []
    };
  },

  created() {
    this.initializeCardsCollection();
  },

  methods: {
    initializeCardsCollection: function() {
      CardService.getAllCards()
        .then(response => {
          return (this.cardsCollection = response.map(r => new Card(r)));
        })
        .then(cardsCollection => {
          this.cardsRarities = this.getCardsRarities(cardsCollection);
        })
        .catch(error => {
          console.error(error);
        });
    },

    getCardsRarities: function(cards) {
      const cardsRarities = [];
      for (const card of cards) {
        if (!cardsRarities.includes(card.rarity)) {
          cardsRarities.push(card.rarity);
        }
      }
      return cardsRarities;
    }
  }
};
</script>

<style scoped>
.card-wrapper {
  display: flex;
  flex-flow: row wrap;
  justify-content: space-between;
  padding-left: 20%;
  padding-right: 20%;
}

.card {
  flex-basis: 24%;
  margin-top: 2%;
  border: none;
}
</style>


