"use client";

import { useCompareStore } from "@/store/useCompareStore";

interface CompareButtonProps {
  stockCode: string;
}

export default function CompareButton({ stockCode }: CompareButtonProps) {
  const stockCodes = useCompareStore((s) => s.stockCodes);
  const add = useCompareStore((s) => s.add);
  const remove = useCompareStore((s) => s.remove);

  const isAdded = stockCodes.includes(stockCode);
  const isFull = stockCodes.length >= 3;

  const handleClick = () => {
    if (isAdded) remove(stockCode);
    else add(stockCode);
  };

  return (
    <button
      onClick={handleClick}
      disabled={!isAdded && isFull}
      className={`rounded-lg border px-3.5 py-2 text-sm transition ${
        isAdded
          ? "border-mint/30 bg-mint/[0.12] text-mint"
          : isFull
            ? "cursor-not-allowed border-line text-fg-3 opacity-50"
            : "border-line text-fg-2 hover:border-mint-dim hover:text-fg"
      }`}
    >
      {isAdded ? "✓" : isFull ? "최대 3개" : "+ 비교"}
    </button>
  );
}