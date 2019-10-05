<template>
  <div class="deck-builder">
    <battle-deck :data="battleDeck"
                 @select-card="openCardsCollection($event)"
                 @generate-random-deck="generateRandomDeck(cardsCollection, deckCardsMaxCount)" />

    <card-collection :data="cardsCollection" />

    <cards-modal :show-collections="showCardsModal"
                 :cards="cardsCollection"
                 :cards-rarities="cardsRarities"
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
      showCardsModal: false
    };
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
      this.showCardsModal = true;
      console.log(cardIdx);
    }
  }
};
</script>

<style scoped>
.deck-builder {
  padding-left: 30%;
  padding-right: 30%;
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
</style>


