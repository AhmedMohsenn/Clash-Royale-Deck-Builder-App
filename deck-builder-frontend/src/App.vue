<template>
  <div class="deck-builder">
    <div class="battle-deck-wrapper">

      <battle-deck :data="battleDeck"
                   @select-mode="openCardsCollection($event)"
                   @remove-mode="removeCardFromBattleDeck($event)"
                   @generate-random-deck="generateRandomDeck(cardsCollection, deckCardsMaxCount)"
                   @share-battle-deck="showShareBattleDeckModal(battleDeck)" />
      <hr>

      <card-collection :data="cardsCollection" />

    </div>

    <deck-statistics :battle-deck="battleDeck"
                     :cards-collection="cardsCollection"
                     :deck-cards-max-count="deckCardsMaxCount" />

    <cards-modal :show-collections="showCardsModal"
                 :cards="cardsCollection"
                 :cards-rarities="cardsRarities"
                 @use-card="useCard($event, currentBattleDeckSlotIdx)"
                 @hide-collections-modal="showCardsModal = false" />

    <b-modal id="share-battle-deck-modal"
             title="Battle Deck Link"
             @show="sharedLink = getShareBattleDeckLink(battleDeck)"
             hide-footer>

      <b-form-textarea type="url"
                       max-rows="3"
                       v-model="sharedLink"
                       readonly></b-form-textarea>
    </b-modal>
  </div>
</template>

<script>
const cardsQueryParamName = "cards";
import { Card } from "@/models/Card.js";
import CardService from "@/services/Card.js";
import CardsModal from "@/components/CardsModal.vue";
import BattleDeck from "@/components/BattleDeck.vue";
import CardCollection from "@/components/CardCollection.vue";
import DeckStatistics from "@/components/DeckStatistics.vue";
export default {
  name: "app",
  components: {
    BattleDeck,
    CardsModal,
    CardCollection,
    DeckStatistics
  },

  data() {
    return {
      cardsCollection: [],
      cardsRarities: [],
      battleDeck: [],
      deckCardsMaxCount: 8,
      showCardsModal: false,
      currentBattleDeckSlotIdx: null,
      sharedLink: null,
      cardsQueryParamValue: this.$route.query[cardsQueryParamName]
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

    loadSharedBattleDeck: function(cardsCollection) {
      const idNames = this.cardsQueryParamValue.split(",");
      for (let idx = 0; idx < idNames.length; idx++) {
        const currentCard = this.getCardByIdName(cardsCollection, idNames[idx]);
        this.useCard(currentCard, idx);
      }
    },

    initializeCardsCollection: function() {
      CardService.getAllCards()
        .then(response => {
          return (this.cardsCollection = response.map(r => new Card(r)));
        })
        .then(cardsCollection => {
          this.cardsRarities = this.getCardsRarities(cardsCollection);
          if (this.cardsQueryParamValue) {
            this.loadSharedBattleDeck(cardsCollection);
          }
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

    getCardByIdName: function(cards, idName) {
      return cards.find(c => c.idName === idName);
    },

    resetBattleDeck: function() {
      for (const card of this.battleDeck) {
        if (card) this.cardsCollection.push(card);
      }
      this.battleDeck = [];
    },

    generateRandomDeck: function(cardsCollection, deckCardsCount) {
      this.resetBattleDeck();
      let idx = 0;
      if (!cardsCollection.length) return;
      while (idx < deckCardsCount) {
        const randomIdx = Math.floor(Math.random() * cardsCollection.length);
        const currentCard = cardsCollection[randomIdx];
        const found = this.battleDeck.find(d => d.id === currentCard.id);
        if (!found) {
          this.useCard(currentCard, idx);
          idx++;
        }
      }
    },

    openCardsCollection: function(cardIdx) {
      this.currentBattleDeckSlotIdx = cardIdx;
      this.showCardsModal = true;
    },

    useCard(chosenCard, cardSlotIdx) {
      this.$set(this.battleDeck, cardSlotIdx, chosenCard);
      this.removeCardFromCardCollection(chosenCard);
      this.showCardsModal = false;
    },

    removeCardFromCardCollection: function(card) {
      this.cardsCollection = this.cardsCollection.filter(c => c.idName !== card.idName);
    },

    removeCardFromBattleDeck: function(cardIdx) {
      const currentCard = this.battleDeck[cardIdx];
      this.$set(this.battleDeck, cardIdx, null);
      this.cardsCollection.push(currentCard);
    },

    showShareBattleDeckModal: function() {
      this.$bvModal.show("share-battle-deck-modal");
    },

    getShareBattleDeckLink: function(battleDeck) {
      const hostPath = `${window.location.host}/#/`;
      let idNames = battleDeck.reduce((acc, card) => acc + card.idName + ",", "");
      idNames = idNames.substring(0, idNames.length - 1);
      let sharedLink = `${hostPath}?${cardsQueryParamName}=${idNames}`;
      return sharedLink;
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
</style>


