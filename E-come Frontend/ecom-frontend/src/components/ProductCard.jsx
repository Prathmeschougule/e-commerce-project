import React, { useState } from "react";
import { FaShoppingCart } from "react-icons/fa";
import ProductViewModel from "./ProductViewModel";

function ProductCard({
  productId,
  productName,
  image,
  productDescription,
  quantity,
  price,
  discount,
  specialPrize,
}) {
  const [openProductViewModel, setOpenProductViewModel] = useState(false);
  const btnLoader = false;
  const [selectViewProduct, setSelectViewProduct] = useState("");

  const isAvailable = quantity && Number(quantity) > 0;

  const handleProductView = (Product) => {
    setSelectViewProduct(Product);
    setOpenProductViewModel(true);
  };

  console.log("Product Props:", {
  productId,
  productName,
  image,
  productDescription,
  quantity,
  price,
  discount,
  specialPrize,
});


  return (
    <div className="border rounded-lg shadow-xl overflow-hidden transition-shadow duration-300">
      <div onClick={() => {
            handleProductView({
              id: productId,
              productName,
              image,
              productDescription,
              quantity,
              price,
              discount,
              specialPrize,
            });
          }} className="w-full overflow-hidden aspect-3/2">
        <img
          src={image}
          alt="ProductName"
          className="w-full h-full cursor-pointer transition-transform duration-300 transform hover:scale-105"
        />
      </div>

      <div className="p-4">
        <h2
          onClick={() => {
            handleProductView({
              id: productId,
              productName,
              image,  
              productDescription,
              quantity,
              price,
              discount,
              specialPrize,
            });
          }}
          className="text-lg font-semibold mb-2 cursor-pointer"
        >
          {productName}
        </h2>
        <div className="min-h-20 max-h-20">
          <p className="text-gray-600 text-sm">{productDescription}</p>
        </div>

        <div className="flex items-center justify-between">
          {specialPrize ? (
            <div className="flex flex-col">
              <span className="text-gray-400 line-through">
                ${Number(price).toFixed(2)}
              </span>
              <span className="text-xl font-bold text-slate-700">
                ${Number(specialPrize).toFixed(2)}
              </span>
            </div>
          ) : (
            <span className="text-xl font-bold text-slate-700">
              {"  "}${Number(price).toFixed(2)}
            </span>
          )}
          <button
            disabled={!isAvailable || btnLoader}
            onClick={()=>{}}
            className={`bg-blue-500 ${
              isAvailable ? "opacity-100 hover:bg-blue-600" : "opacity-70"
            }
                        text-white py-2 px-3 rounded-lg items-center transition-colors duration-300 w-36 flex justify-center`}
          >
            <FaShoppingCart className="mr-2" />
            {isAvailable ? "Add to Card" : "Stock Out"}
          </button>
        </div>
      </div>
      <ProductViewModel
       open={openProductViewModel}
       setOpen={setOpenProductViewModel}
       product={selectViewProduct}
       isAvailable={isAvailable}
      />
    </div>
  );
}

export default ProductCard;
