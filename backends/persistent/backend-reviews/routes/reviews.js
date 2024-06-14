var express = require('express');
const reviewsModel = require('../models/ModelReviews');
var router = express.Router();

/**
 * @swagger
 * components:
 *  schemas:
 *    Review:
 *      type: object
 *      properties:
 *        usuario:
 *          type: String
 *          description: Nombre de usuario
 *        isbn:
 *          type: String
 *          description: ISBN del libro
 *        estrellas:
 *          type: Number
 *          description: Numero de estrellas
 *        comentario:
 *          type: String
 *          description: Comentario realizado al libro
 *      required:
 *        - usuario
 *        - isbn
 *        - estrellas
 *        - comentario
 *      example:
 *        usuario: "mannulus"
 *        isbn: "9789584295446"
 *        estrellas: 2
 *        comentario: "no es muy bueno, muy aburrido, perfiero una pelicula"
 */

/**
 * @swagger
 * /reviews:
 *  get:
 *    summary: Returns the list of all reviews
 *    responses:
 *      200:
 *        description: The list of all reviews
 *        content:
 *          application/json:
 *            schema:
 *              type: array
 *              items:
 *                $ref: '#components/schemas/Review'
 */
router.get('/reviews', async function (req, res, next) {
  console.log("-> request /reviews")
  var docs = await reviewsModel.find({})
  res.json(docs);
});

/* POST users listing. */
router.post('/addreviews', async function (req, res, next) {
  console.log("-> post reviews")
  var doc = await reviewsModel.findOne({ isbn: req.query.isbn, usuario: req.query.usuario });
  if (doc == null) {
    reviewsModel.insertMany(req.query).then((state) => {
      res.json({ code: "OK" });
    })
      .catch((err) => { console.error(err); res.json({ error: err }); });
  } else {
    reviewsModel.findByIdAndUpdate(doc._id, req.query).then((state) => {
      res.json({ code: "OK" });
    })
      .catch((err) => { console.error(err); res.json({ error: err }); });
  }
});

/* DELETE users listing. */
router.delete('/deletereviews', async function (req, res, next) {
  var doc = await reviewsModel.findOne({ isbn: req.query.isbn, usuario: req.query.usuario });
  if (doc == null) {
    res.json({ error: "no existe en la base de datos" });
  } else {
    reviewsModel.deleteOne({ _id: doc._id }).then((state) => {
      res.json({ code: "OK" });
    })
      .catch((err) => { console.error(err); res.json({ error: err }); });
  }
});



module.exports = router;
