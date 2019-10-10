<template>
  <div class="deck-builder">
    <div class="battle-deck-wrapper">

      <battle-deck :data="battleDeck"
                   @select-mode="openCardsCollection($event)"
                   @remove-mode="removeCardFromBattleDeck($event)"
                   @generate-random-deck="generateRandomDeck(cardsCollection, deckCardsMaxCount)" />

      <card-collection :data="cardsCollection" />

    </div>

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

    <cards-modal :show-collections="showCardsModal"
                 :cards="cardsCollection"
                 :cards-rarities="cardsRarities"
                 @use-card="useCard($event)"
                 @hide-collections-modal="showCardsModal = false" />
  </div>
</template>

<script>
import { Card } from "@/models/Card.js";
import CardService from "@/services/Card.js";
import CardsModal from "@/components/CardsModal.vue";
import BattleDeck from "@/components/BattleDeck.vue";
import CardCollection from "@/components/CardCollection.vue";
export default {
  name: "app",
  components: {
    CardsModal,
    CardCollection,
    BattleDeck
  },

  data() {
    return {
      cardsCollection: [],
      cardsRarities: [],
      battleDeck: [],
      deckCardsMaxCount: 8,
      showCardsModal: false,
      currentBattleDeckSlotIdx: null,
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

  created() {
    this.initializeBattleDeck();
    this.initializeCardsCollection();
  },

  methods: {
    initializeBattleDeck: function() {
      for (let i = 0; i < this.deckCardsMaxCount; i++) {
        this.battleDeck.push(null);
      }
    },

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
    },

    getCardsTypes: function(cards) {
      const cardsTypes = [];
      for (const card of cards) {
        if (!cardsTypes.includes(card.type)) {
          cardsTypes.push(card.type);
        }
      }
      return cardsTypes;
    },

    resetBattleDeck: function() {
      for (const card of this.battleDeck) {
        if (card) this.cardsCollection.push(card);
      }
      this.battleDeck = [];
    },

    generateRandomDeck: function(cardsCollection, deckCardsCount) {
      this.resetBattleDeck();
      while (this.battleDeck.length < deckCardsCount) {
        const randomIdx = Math.floor(Math.random() * cardsCollection.length);
        const currentCard = cardsCollection[randomIdx];
        const found = this.battleDeck.find(d => d.id === currentCard.id);
        if (!found) {
          this.battleDeck.push(currentCard);
          cardsCollection.splice(randomIdx, 1);
        }
      }
    },

    openCardsCollection: function(cardIdx) {
      this.currentBattleDeckSlotIdx = cardIdx;
      this.showCardsModal = true;
    },

    useCard(chosenCard) {
      this.$set(this.battleDeck, this.currentBattleDeckSlotIdx, chosenCard);
      this.removeCardFromCardCollection(chosenCard);
      this.showCardsModal = false;
    },

    removeCardFromCardCollection: function(card) {
      this.cardsCollection = this.cardsCollection.filter(c => c.id !== card.id);
    },

    removeCardFromBattleDeck: function(cardIdx) {
      const currentCard = this.battleDeck[cardIdx];
      this.$set(this.battleDeck, cardIdx, null);
      this.cardsCollection.push(currentCard);
    }
  }
};
</script>

<style scoped>
.deck-builder {
  padding-left: 20%;
  padding-right: 5%;
  display: flex;
  flex-flow: row nowrap;
  justify-content: space-between;
}

.battle-deck-wrapper {
  flex-basis: 50%;
}

.deck-statistics {
  flex-basis: 45%;
  margin-top: 4%;
}

.battle-deck {
  margin-bottom: 1%;
}

.battle-deck-header {
  display: flex;
  flex-flow: row nowrap;
  justify-content: space-between;
}

.random-deck-btn {
  color: grey;
  margin: auto 0;
}

.random-deck-btn:hover {
  cursor: pointer;
}

.card-collection-wrapper {
  display: flex;
  flex-flow: row wrap;
  justify-content: space-between;
}

.card {
  flex-basis: 22%;
  margin-top: 2%;
  border: none;
}

.distribution-label {
  font-size: 0.9rem;
  margin-top: 0.6rem;
}
</style>


