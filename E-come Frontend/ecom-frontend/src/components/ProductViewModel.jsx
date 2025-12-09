import React from "react";

import { Button, Dialog, DialogPanel, DialogTitle } from "@headlessui/react";
import { useState } from "react";
import { Divider } from "@mui/material";
import Status from "./Status";
import { MdClose, MdDone } from "react-icons/md";

function ProductViewModel({ open, setOpen, product, isAvailable }) {
  const {
    id,
    productName,
    image,
    description,
    quantity,
    price,
    discount,
    specialPrice,
  } = product;
  const handleClickOpen = () => {
    setOpen(true);
  };
  return (
    <>
      <Dialog
        open={open}
        as="div"
        className="relative z-10 focus:outline-none"
        onClose={close}
        __demoMode
      >
        <div className="fixed inset-0 z-10 w-screen overflow-y-auto">
          <div className="flex min-h-full items-center justify-center p-4">
            <DialogPanel
              transition
              className="w-full max-w-md rounded-xl bg-white p-6 backdrop-blur-2xl duration-300 ease-out data-closed:transform-[scale(95%)] data-closed:opacity-0"
            >
              {image && (
                <div className="flex justify-center aspect-[3/2]">
                  <img
                    src={image}
                    alt="ProductName"
                    className="w-full h-full cursor-pointer transition-transform duration-300 "
                  />
                </div>
              )}

              <div className=" px-6 pt-10 pb-2">
                <DialogTitle
                  as="h1"
                  className="lg:text-3xl sm:text-2xl text-xl font-semibold leading-6 text-gray-800 mb-4"
                >
                  {productName}
                </DialogTitle>
                <div className="space-y-2 text-gray-700 pb-4">
                  <div className="flex items-center justify-between  gap-2 ">
                    {specialPrice ? (
                      <div className="flex flex-col">
                        <span className="text-gray-400 line-through">
                          ${Number(price).toFixed(2)}
                        </span>
                        <span className="text-xl font-bold text-slate-700">
                          ${Number(specialPrice).toFixed(2)}
                        </span>
                      </div>
                    ) : (
                      <span className="text-xl font-bold text-slate-700">
                        {"  "}${Number(price).toFixed(2)}
                      </span>
                    )}

                    {isAvailable ? (
                      
                        <Status
                          text="In Stock"
                          icon={MdDone}
                          bg="bg-teal-200"
                          color="text-teal-900"
                        />
                      
                    ):(
                      <Status
                          text="Out Of Stock"
                          icon={MdClose}
                          bg="bg-rose-200"
                          color="text-rose-700"
                        />
                    )}
                  </div>
                  <Divider/>
                  <p>{description}</p>
                </div>
                <div className="mt-1 flex justify-end">
                  <Button
                    className="inline-flex items-center gap-2 rounded-md bg-gray-700 px-3 py-1.5 text-sm/6 font-semibold text-white shadow-inner shadow-white/10 focus:not-data-focus:outline-none data-focus:outline data-focus:outline-white data-hover:bg-gray-600 data-open:bg-gray-700"
                    onClick={() => setOpen(false)}
                  >
                    Close
                  </Button>
                </div>
              </div>
            </DialogPanel>
          </div>
        </div>
      </Dialog>
    </>
  );
}

export default ProductViewModel;
