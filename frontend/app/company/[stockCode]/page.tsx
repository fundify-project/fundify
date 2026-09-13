import { getCompanyDetail, getCurrentPrice } from "@/lib/api";
import CompanyHeader from "@/components/company/CompanyHeader";
import PriceChart from "@/components/company/PriceChart";
import MetricCards from "@/components/company/MetricCards";
import FinancialTable from "@/components/company/FinancialTable";

interface CompanyPageProps {
  params: Promise<{ stockCode: string }>;
}

export default async function CompanyPage({ params }: CompanyPageProps) {
  const { stockCode } = await params;

  const [data, price] = await Promise.all([
    getCompanyDetail(stockCode),
    getCurrentPrice(stockCode),
  ]);

  return (
    <main className="px-6 py-10">
      <div className="mx-auto flex max-w-4xl flex-col gap-4">
        <CompanyHeader info={data.info} price={price} />
        <PriceChart stockCode={stockCode} />
        <MetricCards metrics={data.metrics ?? []} />
        <FinancialTable statements={data.financials ?? []} />
      </div>
    </main>
  );
}
