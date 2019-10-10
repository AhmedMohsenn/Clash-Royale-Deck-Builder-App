<template>
  <div class="deck-statistics">
    <h6 class="distribution-label">Average Elixir Cost</h6>
    <b-progress height="2rem"
                :value="getAverageElixirCost"
                :max="getMaxCardByElixir"
                :precision="1"
                show-value></b-progress>

    <h6 class="distribution-label">Minimum Cycle Cost </h6>
    <b-progress height="2rem"
                :value="getMinCycleCost"
                :max="getMaxCardByElixir * 4"
                show-value></b-progress>

    <h6 class="distribution-label">Card Types</h6>
    <b-progress height="2rem"
                :max="deckCardsMaxCount">
      <b-progress-bar v-for="(d, idx) of getCardsTypesDistribution"
                      v-bind:key="d.type"
                      :value="d.typeCount"
                      :variant="progressBarColor[idx]">
        {{d.typeCount}} {{d.type}}
      </b-progress-bar>
    </b-progress>

    <h6 class="distribution-label">Card Rarities</h6>
    <b-progress height="2rem"
                :max="deckCardsMaxCount">
      <b-progress-bar v-for="(d, idx) of getCardsRaritiesDistribution"
                      v-bind:key="d.rarity"
                      :value="d.rarityCount"
                      :variant="progressBarColor[idx]">
        {{d.rarityCount}} {{d.rarity}}
      </b-progress-bar>
    </b-progress>
  </div>
</template>

<script>
export default {
  name: "deck-statistics",
  props: {
    battleDeck: Array,
    cardsCollection: Array,
    deckCardsMaxCount: Number
  },

  data() {
    return {
      progressBarColor: ["success", "info", "warning", "danger", "primary", "secondary", "dark"]
    };
  },

  computed: {
    getMaxCardByElixir: function() {
      const maxElixir = this.cardsCollection.reduce((acc, card) => {
        return card ? Math.max(acc, Number(card.elixirCost)) : acc;
      }, 0);
      return maxElixir;
    },
    getNumberofCardsInBattleDeck: function() {
      const numberOfCards = this.battleDeck.reduce((acc, card) => {
        return card ? acc + 1 : acc;
      }, 0);
      return numberOfCards;
    },
    getTotalSumOfElixirInBattleDeck: function() {
      const sum = this.battleDeck.reduce((acc, card) => {
        return card ? acc + card.elixirCost : acc;
      }, 0);
      return sum;
    },
    getAverageElixirCost: function() {
      if (this.getNumberofCardsInBattleDeck === 0) return 0;
      return this.getTotalSumOfElixirInBattleDeck / this.getNumberofCardsInBattleDeck;
    },
    getMinCycleCost: function() {
      const battleDeckCopy = [...this.battleDeck];
      battleDeckCopy.sort(function(c1, c2) {
        if (!c1) return 1;
        if (!c2) return -1;
        if (c1.elixirCost < c2.elixirCost) return -1;
        if (c1.elixirCost > c2.elixirCost) return 1;
        return 0;
      });

      let cost = 0;
      for (let i = 0; i < 4; i++) {
        if (battleDeckCopy[i]) cost += battleDeckCopy[i].elixirCost;
      }
      return cost;
    },
    getCardsTypesDistribution: function() {
      const distribution = [];
      const cardsTypes = this.getCardsTypes(this.cardsCollection);
      cardsTypes.forEach(type => {
        const typeCount = this.battleDeck.filter(c => c && c.type === type).length;
        distribution.push({ type, typeCount });
      });
      return distribution;
    },
    getCardsRaritiesDistribution: function() {
      const distribution = [];
      const cardsRarities = this.getCardsRarities(this.cardsCollection);
      cardsRarities.forEach(rarity => {
        const rarityCount = this.battleDeck.filter(c => c && c.rarity === rarity).length;
        distribution.push({ rarity, rarityCount });
      });
      return distribution;
    }
  },

  methods: {
    getCardsRarities: function(cards) {
      const cardsRarities = [];
      for (const card of cards) {
        if (!cardsRarities.includes(card.rarity)) {
          cardsRarities.push(card.rarity);
        }
      }
      return cardsRarities;
    },

    getCardsTypes: function(cards) {
      const cardsTypes = [];
      for (const card of cards) {
        if (!cardsTypes.includes(card.type)) {
          cardsTypes.push(card.type);
        }
      }
      return cardsTypes;
    }
  }
};
</script>

<style scoped>
.distribution-label {
  font-size: 0.9rem;
  margin-top: 0.8rem;
}
</style>
