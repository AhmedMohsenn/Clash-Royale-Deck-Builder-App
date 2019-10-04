export class Card {
  constructor(card) {
    this._arena = card.arena;
    this._description = card.description;
    this._elixirCost = card.elixirCost;
    this._idName = card.idName;
    this._name = card.name;
    this._rarity = card.rarity;
    this._type = card.type;
    this._id = card._id;
  }

  get area() {
    return this._arena;
  }
  get description() {
    return this._description;
  }
  get elixirCost() {
    return this._elixirCost;
  }
  get idName() {
    return this._idName;
  }
  get name() {
    return this._name;
  }
  get rarity() {
    return this._rarity;
  }
  get type() {
    return this._type;
  }
  get id() {
    return this._id;
  }
}
